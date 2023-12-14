package com.timeOrganizer.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.model.dto.request.GoogleAuthRequest;
import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.LoginResponse;
import com.timeOrganizer.model.dto.response.GoogleAuthResponse;
import com.timeOrganizer.model.dto.response.RegistrationResponse;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUserRepository;
import com.timeOrganizer.security.UserRole;
import com.timeOrganizer.security.config.JwtService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final GoogleAuthenticator gAuth;
    @Value("${token.expirationLong}")
    private int TOKEN_EXPIRATION_IN_HOURS_LONG;
    @Value("${token.expirationShort}")
    private int TOKEN_EXPIRATION_IN_HOURS_SHORT;

    public RegistrationResponse registerUser(RegistrationRequest registration) throws PersistenceException, IOException {
        RegistrationResponse response;
        User newUser = User.builder()
                .name(registration.getName())
                .surname(registration.getSurname())
                .email(registration.getEmail())
                .password(new BCryptPasswordEncoder().encode(registration.getPassword()))
                .role(UserRole.USER)
                .build();
        if (registration.isUse2FA()) {
            GoogleAuthenticatorKey credentials = gAuth.createCredentials();
            newUser.setSecretKey2FA(credentials.getKey());
            newUser.setScratchCodes(credentials.getScratchCodes());
            var test = credentials.getVerificationCode();
            try {
                response = RegistrationResponse.builder()
                        .email(newUser.getEmail())
                        .has2FA(true)
                        .qrCode(this.generate2FaQrCode(newUser.getEmail(), credentials))
                        .build();
            } catch (Exception e) {
                throw new IOException();
            }
        } else {
            response = RegistrationResponse.builder()
                    .email(newUser.getEmail())
                    .has2FA(false).build();
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
        if (user.has2FA()) {
            //save secretKey2Fa to session by email
            return LoginResponse.builder()
                    .email(loginRequest.getEmail())
                    .has2FA(true)
                    .build();
        } else {
            String jwtToken = jwtService.generateToken(loginRequest.getEmail(), this.getLengthOfTokenExpiration(loginRequest.isStayLoggedIn()));
            return LoginResponse.builder()
                    .token(jwtToken)
                    .email(loginRequest.getEmail())
                    .has2FA(false)
                    .build();
        }
    }
    public GoogleAuthResponse validate2FA(GoogleAuthRequest request) {
        User user = findByEmail(request.getEmail());
        if (gAuth.authorize(user.getSecretKey2FA(), Integer.parseInt(request.getCode()))) {
            String jwtToken = jwtService.generateToken(request.getEmail(), this.getLengthOfTokenExpiration(request.isStayLoggedIn()));
            return new GoogleAuthResponse(true,jwtToken);
        } else {
            return new GoogleAuthResponse(false,null);
        }
    }

    public void logout(String token) {
        String actualToken = token.substring(7);
        jwtService.invalidateToken(actualToken);
    }



    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    private int getLengthOfTokenExpiration(boolean stayLoggedIn) {
        return stayLoggedIn ? TOKEN_EXPIRATION_IN_HOURS_LONG : TOKEN_EXPIRATION_IN_HOURS_SHORT;
    }
}