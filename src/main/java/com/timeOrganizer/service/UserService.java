package com.timeOrganizer.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.model.dto.mappers.UserMapper;
import com.timeOrganizer.model.dto.request.*;
import com.timeOrganizer.model.dto.response.*;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUserRepository;
import com.timeOrganizer.security.UserRole;
import com.timeOrganizer.security.config.JwtService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthenticator gAuth;
    private final EmailService emailService;
    private final UserMapper userMapper;
    @Value("${token.expirationLong}")
    private int TOKEN_EXPIRATION_IN_HOURS_LONG;
    @Value("${token.expirationShort}")
    private int TOKEN_EXPIRATION_IN_HOURS_SHORT;
    @Value("${app.name}")
    private String APP_NAME;

    public RegistrationResponse registerUser(RegistrationRequest registration) throws PersistenceException, IOException {
        RegistrationResponse response;
        User newUser = User.builder().name(registration.getName()).surname(registration.getSurname()).email(registration.getEmail()).password(new BCryptPasswordEncoder().encode(registration.getPassword())).role(UserRole.USER).build();
        if (registration.isUse2FA()) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            newUser.setSecretKey2FA(credentials.getKey());
            newUser.setScratchCodes(credentials.getScratchCodes());
            try {
                response = RegistrationResponse.builder().email(newUser.getEmail()).has2FA(true).qrCode(this.generate2FaQrCode(newUser.getEmail(), credentials)).build();
            } catch (Exception e) {
                throw new IOException();
            }
        } else {
            response = RegistrationResponse.builder().email(newUser.getEmail()).has2FA(false).build();
        }
        try {
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return response;
    }

    private byte[] generate2FaQrCode(String email, GoogleAuthenticatorKey key) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", email, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return outputStream.toByteArray();
    }

    public LoginResponse loginUser(LoginRequest loginRequest) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        User user = findByEmail(loginRequest.getEmail());
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

    public GoogleAuthResponse validate2FALogin(GoogleAuthLoginRequest request) throws EntityNotFoundException {
        User user = findByEmail(request.getEmail());
        if (gAuth.authorize(user.getSecretKey2FA(), Integer.parseInt(request.getCode()))) {
            String jwtToken = jwtService.generateToken(user.getEmail(), user.getId(), this.getLengthOfTokenExpiration(user.isStayLoggedIn()));
            return new GoogleAuthResponse(true, jwtToken);
        } else {
            return new GoogleAuthResponse(false, null);
        }
    }

    public void logout(String token) {
        jwtService.invalidateToken(token);
    }

    public void resetPassword(String email) throws EntityNotFoundException {
        String tempPassword = this.generateTemporaryPassword();
        int test = userRepository.updatePasswordByEmail(email, new BCryptPasswordEncoder().encode(tempPassword));
        if (test == 0) {
            throw new EntityNotFoundException();
        }
        String emailBody = emailService.generateForgottenPasswordEmail(tempPassword);
        emailService.sendEmail(email, "Password reset - " + APP_NAME, emailBody);
    }

    //TODO Check if no changes made so no other requests needed
    public boolean wereSensitiveChangesMade(UserResponse loggedUser, UserRequest changedUser) {
        return !(loggedUser.getEmail().equals(changedUser.getEmail()) && loggedUser.isHas2FA() == changedUser.isHas2FA());
    }
    public boolean verifyPasswordAndReturn2FAStatus(String token, String password) throws AuthenticationException {
        User loggedUser = getLoggedUser(token);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedUser.getEmail(), password));
        return loggedUser.has2FA();
    }
    public boolean validate2FA(String token, int code) throws EntityNotFoundException, NumberFormatException {
        User loggedUser = getLoggedUser(token);
        return gAuth.authorize(loggedUser.getSecretKey2FA(), code);
    }
    public EditedUserResponse editLoggedUserData(String token, UserRequest request) throws EntityNotFoundException, NumberFormatException, IOException {
        User loggedUser = getLoggedUser(token);
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
            try {
                qrCode = this.generate2FaQrCode(loggedUser.getEmail(), credentials);
            } catch (Exception e) {
                throw new IOException();
            }
        }
        loggedUser = userMapper.editFromRequest(loggedUser, request);
        userRepository.save(loggedUser);
        return userMapper.convertToEditedUserSettingsResponse(loggedUser, newToken, qrCode);
    }
    public void changeLoggedUserPassword(String token, String newPassword) throws EntityNotFoundException {
        User loggedUser = getLoggedUser(token);
        loggedUser.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(loggedUser);
    }
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
    public byte[] get2FAQrCode(String token) throws EntityNotFoundException, NumberFormatException, IOException {
        User loggedUser = getLoggedUser(token);
        byte[] qrCode;
        try {
            GoogleAuthenticatorKey credentials = new GoogleAuthenticatorKey.Builder(loggedUser.getSecretKey2FA())
                    .setScratchCodes(loggedUser.getScratchCodes())
                    .build();
            qrCode = this.generate2FaQrCode(loggedUser.getEmail(), credentials);
        } catch (Exception e) {
            throw new IOException();
        }
        return qrCode;
    }
    //TODO vratit novy qr
    public int get2FAScratchCode(String token) throws EntityNotFoundException, NumberFormatException, IOException {
        User loggedUser = getLoggedUser(token);
        int scratchCode;
        byte[] qrCode;
        try {
            var scratchCodes = loggedUser.getScratchCodes();
            scratchCode = scratchCodes.remove(0);
            loggedUser.setScratchCodes(scratchCodes);
        } catch (IndexOutOfBoundsException e) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            loggedUser.setSecretKey2FA(credentials.getKey());
            loggedUser.setScratchCodes(credentials.getScratchCodes());
            try {
                qrCode = this.generate2FaQrCode(loggedUser.getEmail(),credentials);
            } catch (Exception ex) {
                throw new IOException(ex);
            }
            scratchCode = -1;
        }
        userRepository.save(loggedUser);
        return scratchCode;
    }

    //=================================HELPER METHODS================================================================

    public User getLoggedUser(String token) throws EntityNotFoundException, NumberFormatException {
        long id;
//        try {
            id = jwtService.extractId(token);
//        } catch (NumberFormatException exception) {
//            throw new NumberFormatException("Error extracting id from token " + jwtService.extractId(token));
//        }
        return this.findById(id);
    }
    public User findById(long id) throws UserNotFoundException {
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}