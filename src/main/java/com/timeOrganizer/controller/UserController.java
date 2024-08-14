package com.timeOrganizer.controller;

import com.timeOrganizer.controller.generic.MyController;
import com.timeOrganizer.exception.QrCode2FAGenerationException;
import com.timeOrganizer.helper.EmailService;
import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.user.*;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.model.dto.response.user.*;
import com.timeOrganizer.security.config.OAuth2Service;
import com.timeOrganizer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.*;

@JsonRequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController extends MyController
{
    private final UserService userService;
    private final OAuth2Service oAuth2Service;
    private final EmailService emailService;
    //    private final HttpSession session;
    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) throws QrCode2FAGenerationException {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.registerUser(request));
    }
    @PostMapping("/auth/oauth2/callback")
    public ResponseEntity<String> handleOAuth2Callback(@RequestBody OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        try {
            // Exchange authorization code for OAuth2User
            oAuth2Service.getUserInfo(authorizationGrantRequest);
//            OAuth2User oAuth2User =

            // Here, you can generate and return a JWT token as needed
//            String jwtToken = generateJwtToken(oAuth2User);

            return ResponseEntity.ok("ad");
        } catch (OAuth2AuthenticationException e) {
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        //emailService.sendSimpleEmail("jakubsobota123@gmail.com","TESTTESTTESTTEST","test123");
        return ResponseEntity.ok(userService.loginUser(request));
    }
    @PostMapping("/auth/validate2FA")
    public ResponseEntity<GoogleAuthResponse> validate2FALogin(@RequestBody GoogleAuthLoginRequest request) {
        return ResponseEntity.ok(userService.validate2FALogin(request));
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("Logout successful");
    }
    @PostMapping("/auth/forgotten-password")
    public ResponseEntity<String> forgottenPassword(@RequestBody EmailRequest request) {
        try {
            userService.resetPassword(request.getEmail());
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error resetting password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("Temporary password sent to user's email");
    }
    @PostMapping("/were-sensitive-changes-made")
    public ResponseEntity<?> wereSensitiveChangesMade(@RequestBody UserRequest request) {
        boolean needs2FA;
        try {
            needs2FA = userService.wereSensitiveChangesMade(request);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting 2FA status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(needs2FA);
    }
    @PostMapping("/verify-password")
    public ResponseEntity<?> verifyPassword(@RequestHeader("Authorization") String token, @RequestBody @NotNull String password) {
        boolean needs2FA;
        password = (String) this.oneParamRequestValue(password, true);
        try {
            needs2FA = userService.verifyPasswordAndReturn2FAStatus(token, password);
        } catch (AuthenticationException authenticationException) {
            return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting 2FA status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(this.oneParamResponse("needs2FA", needs2FA));
    }
    @PostMapping("/validate2FA")
    public ResponseEntity<?> validate2FA(@RequestHeader("Authorization") String token, @RequestBody String code) {
        boolean valid2FA;
        try {
            int intCode = (int) this.oneParamRequestValue(code, false);
            valid2FA = userService.validate2FA(token, intCode);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error getting 2FA status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(this.oneParamResponse("valid2FA", valid2FA));
    }
    @PostMapping("/get-logged-user-data")
    public ResponseEntity<UserResponse> getLoggedInUserData() {
        return ResponseEntity.ok(userService.getLoggedUserData());
    }
    @PutMapping("/edit-logged-user-data")
    public ResponseEntity<EditedUserResponse> editLoggedInUserData(@RequestHeader("Authorization") String token, @RequestBody UserRequest request) throws QrCode2FAGenerationException{
        return ResponseEntity.ok(userService.editLoggedUserData(token, request));
    }

    @PutMapping("/change-locale")
    public ResponseEntity<String> changeLocale(@RequestBody() LocaleRequest localeRequest)
    {
        userService.changeCurrentLocale(localeRequest.getLocale());
        return ResponseEntity.ok("Locale changed");
    }
    @PostMapping("/change-password")
    public ResponseEntity<SuccessResponse> changePassword(@RequestHeader("Authorization") String token, @RequestBody String password) {
        password = (String) this.oneParamRequestValue(password, true);
        userService.changeLoggedUserPassword(token, password);
        return ResponseEntity.ok(new SuccessResponse("Password changed successfully"));
    }
    @DeleteMapping("/delete-my-account")
    public ResponseEntity<SuccessResponse> deleteLoggedUserAccount(@RequestHeader("Authorization") String token) {
        userService.deleteLoggedUserAccount(token);
        return ResponseEntity.ok(new SuccessResponse("Account deleted successfully"));
    }
    @PostMapping("/get-2fa-qr-code")
    public ResponseEntity<byte[]> get2FAQrCode(@RequestHeader("Authorization") String token) throws QrCode2FAGenerationException{
        return ResponseEntity.ok(userService.get2FAQrCode(token));
    }
    @PostMapping("/get-2fa-scratch-code")
    public ResponseEntity<?> get2FAScratchCode(@RequestHeader("Authorization") String token) throws QrCode2FAGenerationException {
        int scratchCode = userService.get2FAScratchCode(token);
        if (scratchCode == -1) {
            return ResponseEntity.ok(this.oneParamResponse("new2FAQrCode", true));
        } else {
            return ResponseEntity.ok(this.oneParamResponse("scratchCode", scratchCode));
        }
    }
}
