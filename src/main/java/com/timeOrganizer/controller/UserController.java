package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.*;
import com.timeOrganizer.model.dto.response.*;
import com.timeOrganizer.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(path = "/api/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class UserController extends MyController {
    private final UserService userService;
    private final HttpSession session;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        RegistrationResponse response;
        try {
            response = userService.registerUser(request);
        } catch (PersistenceException persistenceException) {
            return new ResponseEntity<>(new ErrorResponse("User exists", persistenceException.getMessage()), HttpStatus.CONFLICT);
        } catch (IOException exception) {
            return new ResponseEntity<>(new ErrorResponse("Error with creating 2FA QR code", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response;
        try {
            response = userService.loginUser(request);
        } catch (AuthenticationException authenticationException) {
            return new ResponseEntity<>(new ErrorResponse("Wrong credentials", authenticationException.getMessage()), HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ErrorResponse("User exists", exception.getMessage()), HttpStatus.LOCKED);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/validate2FA")
    public ResponseEntity<GoogleAuthResponse> validate2FA(@RequestBody GoogleAuthLoginRequest request) {
        return ResponseEntity.ok(userService.validate2FALogin(request));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/auth/forgotten-password")
    public ResponseEntity<String> forgottenPassword(@RequestBody ForgotPasswordRequest request) {
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
            needs2FA = userService.wereSensitiveChangesMade(this.getLoggedUserFromSession(), request);
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
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
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
    public ResponseEntity<IResponse> getLoggedInUserData() {
        UserResponse user;
        try {
            user = this.getLoggedUserFromSession();
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(user);
    }
    @PutMapping("/edit-logged-user-data")
    public ResponseEntity<IResponse> editLoggedInUserData(@RequestHeader("Authorization") String token, @RequestBody UserRequest request) {
        EditedUserResponse user;
        try {
            user = userService.editLoggedUserData(token, request);
            UserResponse loggedUser = user;
            session.setAttribute("loggedUser", loggedUser);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/change-password")
    public ResponseEntity<IResponse> changePassword(@RequestHeader("Authorization") String token, @RequestBody String password) {
        try {
            password = (String) this.oneParamRequestValue(password, true);
            userService.changeLoggedUserPassword(token, password);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new SuccessResponse("Password changed successfully"));
    }

    @DeleteMapping("/delete-my-account")
    public ResponseEntity<IResponse> deleteLoggedUserAccount(@RequestHeader("Authorization") String token) {
        try {
            userService.deleteLoggedUserAccount(token);
            session.removeAttribute("loggedUser");
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>(new ErrorResponse("Token ID ERROR", numberFormatException.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(new SuccessResponse("Account deleted successfully"));
    }

    @PostMapping("/get-2fa-qr-code")
    public ResponseEntity<?> get2FAQrCode(@RequestHeader("Authorization") String token) {
        byte[] qrCode;
        try {
            qrCode = userService.get2FAQrCode(token);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(qrCode);
    }

    @PostMapping("/get-2fa-scratch-code")
    public ResponseEntity<?> get2FAScratchCode(@RequestHeader("Authorization") String token) {
        int scratchCode;
        try {
            scratchCode = userService.get2FAScratchCode(token);
        } catch (EntityNotFoundException notFoundException) {
            return new ResponseEntity<>(new ErrorResponse("User NOT FOUND", notFoundException.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Uncaught error", "Error resetting password"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (scratchCode == -1) {
            return ResponseEntity.ok(this.oneParamResponse("new2FAQrCode", true));
        } else {
            return ResponseEntity.ok(this.oneParamResponse("scratchCode", scratchCode));
        }
    }
    private UserResponse getLoggedUserFromSession() throws Exception {
        UserResponse loggedUser = (UserResponse) session.getAttribute("loggedUser");
        if(loggedUser == null){
            throw new Exception("e");
        }
        return loggedUser;
    }
}
