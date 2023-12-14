package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.GoogleAuthRequest;
import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.LoginResponse;
import com.timeOrganizer.model.dto.response.ErrorResponse;
import com.timeOrganizer.model.dto.response.GoogleAuthResponse;
import com.timeOrganizer.model.dto.response.RegistrationResponse;
import com.timeOrganizer.service.UserService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController extends MyController {
    private final UserService userService;

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
    public ResponseEntity<GoogleAuthResponse> validate2FA(@RequestBody GoogleAuthRequest request) {
        return ResponseEntity.ok(userService.validate2FA(request));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        userService.logout(token);
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/auth/forgotten-password")
    public ResponseEntity<String> forgottenPassword(String email) {
        userService.findByEmail(email);
        //TODO
        return ResponseEntity.ok("Logout successful");
    }


}
