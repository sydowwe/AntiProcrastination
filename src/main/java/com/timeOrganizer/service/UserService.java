package com.timeOrganizer.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.timeOrganizer.exception.IdInTokenFormatException;
import com.timeOrganizer.exception.QrCode2FAGenerationException;
import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.helper.EmailService;
import com.timeOrganizer.model.dto.mappers.UserMapper;
import com.timeOrganizer.model.dto.request.user.GoogleAuthLoginRequest;
import com.timeOrganizer.model.dto.request.user.LoginRequest;
import com.timeOrganizer.model.dto.request.user.RegistrationRequest;
import com.timeOrganizer.model.dto.request.user.UserRequest;
import com.timeOrganizer.model.dto.response.user.*;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUserRepository;
import com.timeOrganizer.security.LoggedUser;
import com.timeOrganizer.security.UserRole;
import com.timeOrganizer.security.config.JwtService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthenticator gAuth;
    private final EmailService emailService;
    private final UserMapper userMapper;
    private final UrgencyService urgencyService;
    private final RoutineToDoListTimePeriodTimePeriodService routineToDoListTimePeriodService;
    @Value("${token.expirationLong}")
    private int TOKEN_EXPIRATION_IN_HOURS_LONG;
    @Value("${token.expirationShort}")
    private int TOKEN_EXPIRATION_IN_HOURS_SHORT;
    @Value("${app.name}")
    private String APP_NAME;
    @Override
    public RegistrationResponse registerUser(RegistrationRequest registration) throws PersistenceException, QrCode2FAGenerationException {
        RegistrationResponse response;
        User newUser = User.builder().name(registration.getName()).surname(registration.getSurname()).email(registration.getEmail()).password(passwordEncoder.encode(registration.getPassword())).role(UserRole.USER).build();
        if (registration.isHas2FA()) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            newUser.setSecretKey2FA(credentials.getKey());
            newUser.setScratchCodes(credentials.getScratchCodes());
            response = RegistrationResponse.builder().email(newUser.getEmail()).has2FA(true).qrCode(this.generate2FaQrCode(newUser.getEmail(), credentials)).build();

        } else {
            response = RegistrationResponse.builder().email(newUser.getEmail()).has2FA(false).build();
        }
        try {
            userRepository.save(newUser);
            this.setDefaultSettings(newUser);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return response;
    }

    private byte[] generate2FaQrCode(String email, GoogleAuthenticatorKey key) throws QrCode2FAGenerationException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(APP_NAME, email, key);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 120, 120);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        } catch (Exception e) {
            throw new QrCode2FAGenerationException(e);
        }
        return outputStream.toByteArray();
    }
    @Override
    public Oauth2LoginResponse oauth2LoginUser(OAuth2User oAuth2User) throws AuthenticationException, UserNotFoundException {
        String email = oAuth2User.getName();
        User user = this.findByEmail(email);
        if (user.has2FA()) {
            //save secretKey2Fa to session by email
            return Oauth2LoginResponse.builder().id(user.getId()).email(email).has2FA(true).authenticated(true).build();
        } else {
            String jwtToken = jwtService.generateToken(user.getEmail(), user.getId(), this.getLengthOfTokenExpiration(user.isStayLoggedIn()));
            return Oauth2LoginResponse.builder().id(user.getId()).token(jwtToken).email(email).authenticated(true).has2FA(false).build();
        }
    }
    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) throws AuthenticationException, UserNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = this.findByEmail(loginRequest.getEmail());
        if (user.isStayLoggedIn() != loginRequest.isStayLoggedIn()) {
            userRepository.updateStayLoggedInById(user.getId(), loginRequest.isStayLoggedIn());
        }
        if (user.has2FA()) {
            //save secretKey2Fa to session by email
            return LoginResponse.builder().id(user.getId()).email(loginRequest.getEmail()).has2FA(true).build();
        } else {
            String jwtToken = jwtService.generateToken(user.getEmail(), user.getId(), this.getLengthOfTokenExpiration(loginRequest.isStayLoggedIn()));
            return LoginResponse.builder().id(user.getId()).token(jwtToken).email(loginRequest.getEmail()).has2FA(false).build();
        }
    }
    @Override
    public GoogleAuthResponse validate2FALogin(GoogleAuthLoginRequest request) throws UserNotFoundException {
        User user = this.findByEmail(request.getEmail());
        if (gAuth.authorize(user.getSecretKey2FA(), Integer.parseInt(request.getCode()))) {
            String jwtToken = jwtService.generateToken(user.getEmail(), user.getId(), this.getLengthOfTokenExpiration(user.isStayLoggedIn()));
            return new GoogleAuthResponse(true, jwtToken);
        } else {
            return new GoogleAuthResponse(false, null);
        }
    }
    @Override
    public void logout(String token) {
        jwtService.invalidateToken(token);
    }
    @Override
    public void resetPassword(String email) throws UserNotFoundException {
        String tempPassword = this.generateTemporaryPassword();
        int test = userRepository.updatePasswordByEmail(email, new BCryptPasswordEncoder().encode(tempPassword));
        if (test == 0) {
            throw new EntityNotFoundException();
        }
        String emailBody = emailService.generateForgottenPasswordEmail(tempPassword);
        emailService.sendEmail(email, "Password reset - " + APP_NAME, emailBody);
    }

    //TODO Check if no changes made so no other requests needed
    @Override
    public boolean wereSensitiveChangesMade(LoggedUser loggedUser, UserRequest changedUser) {
        return !(loggedUser.getEmail().equals(changedUser.getEmail()) && loggedUser.isHas2FA() == changedUser.isHas2FA());
    }
    @Override
    public boolean verifyPasswordAndReturn2FAStatus(String token, String password) throws AuthenticationException {
        User loggedUser = getLoggedUser(token);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getEmail(), password));
        return loggedUser.has2FA();
    }
    @Override
    public boolean validate2FA(String token, int code) throws EntityNotFoundException, NumberFormatException {
        User loggedUser = getLoggedUser(token);
        return gAuth.authorize(loggedUser.getSecretKey2FA(), code);
    }
    @Override
    public UserResponse getLoggedUserData(LoggedUser loggedUser) {
        return userMapper.convertToUserSettingsResponse(loggedUser);
    }
    @Override
    public EditedUserResponse editLoggedUserData(String token, UserRequest request) throws EntityNotFoundException, NumberFormatException,
            QrCode2FAGenerationException {
        User loggedUser = this.getLoggedUser(token);
        String newToken = null;
        byte[] qrCode = null;
        if (!loggedUser.getEmail().equals(request.getEmail())) {
            jwtService.invalidateToken(token);
            newToken = jwtService.generateToken(request.getEmail(), loggedUser.getId(), this.getLengthOfTokenExpiration(loggedUser.isStayLoggedIn()));
        }
        if (!loggedUser.has2FA() && request.isHas2FA()) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            loggedUser.setSecretKey2FA(credentials.getKey());
            loggedUser.setScratchCodes(credentials.getScratchCodes());
            qrCode = this.generate2FaQrCode(loggedUser.getEmail(), credentials);
        }
        loggedUser = userMapper.updateEntityFromRequest(loggedUser, request);
        userRepository.save(loggedUser);
        return userMapper.convertToEditedUserSettingsResponse(loggedUser, newToken, qrCode);
    }
    @Override
    public void changeLoggedUserPassword(String token, String newPassword) throws EntityNotFoundException {
        User loggedUser = getLoggedUser(token);
        loggedUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(loggedUser);
    }
    @Override
    public void deleteLoggedUserAccount(String token) throws EntityNotFoundException, NumberFormatException {
        try {
            long id = jwtService.extractId(token);
            userRepository.deleteById(id);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("Error extracting id from token " + jwtService.extractId(token));
        } catch (EntityNotFoundException exception) {
            throw new EntityNotFoundException("Entity with id: " + jwtService.extractId(token) + "NOT FOUND");
        }
    }
    @Override
    public byte[] get2FAQrCode(String token) throws EntityNotFoundException, NumberFormatException, QrCode2FAGenerationException {
        User loggedUser = getLoggedUser(token);
        GoogleAuthenticatorKey credentials = new GoogleAuthenticatorKey.Builder(loggedUser.getSecretKey2FA())
                .setScratchCodes(loggedUser.getScratchCodes())
                .build();
        return this.generate2FaQrCode(loggedUser.getEmail(), credentials);
    }

    //TODO Create new qr when no scratchCodes left
    @Override
    public int get2FAScratchCode(String token) throws EntityNotFoundException, NumberFormatException, QrCode2FAGenerationException {
        User loggedUser = getLoggedUser(token);
        int scratchCode;
        try {
            var scratchCodes = loggedUser.getScratchCodes();
            scratchCode = scratchCodes.removeFirst();
            loggedUser.setScratchCodes(scratchCodes);
        } catch (IndexOutOfBoundsException e) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            loggedUser.setSecretKey2FA(credentials.getKey());
            loggedUser.setScratchCodes(credentials.getScratchCodes());
            byte[] qrCode = this.generate2FaQrCode(loggedUser.getEmail(), credentials);
            scratchCode = -1;
        }
        userRepository.save(loggedUser);
        return scratchCode;
    }

    //=================================HELPER METHODS================================================================
    private User getLoggedUser(String token) throws UserNotFoundException, IdInTokenFormatException {
        return this.findById(jwtService.extractId(token));
    }

    private User findById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    private int getLengthOfTokenExpiration(boolean stayLoggedIn) {
        return stayLoggedIn ? TOKEN_EXPIRATION_IN_HOURS_LONG : TOKEN_EXPIRATION_IN_HOURS_SHORT;
    }

    private String generateTemporaryPassword() {
        byte[] randomBytes = new byte[16];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    private void setDefaultSettings(User user) throws EntityExistsException, RollbackException {
        urgencyService.createDefaultItems(user);
        routineToDoListTimePeriodService.createDefaultItems(user);
    }
}