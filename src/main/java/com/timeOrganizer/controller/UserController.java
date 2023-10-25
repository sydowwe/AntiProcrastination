package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.AuthenticationResponse;
import com.timeOrganizer.model.dto.response.ErrorResponse;
import com.timeOrganizer.service.UserService;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController extends MyController {
    private final UserService userService;
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request){
        if(request==null){
            return ResponseEntity.badRequest().build();
        }
        AuthenticationResponse response;
        try {
            response = userService.registerUser(request);
        }
        catch (InvalidKeyException invalidKeyException){
            return new ResponseEntity<>(new ErrorResponse("Invalid secret key",invalidKeyException.getMessage()),HttpStatus.CONFLICT);
        }catch (PersistenceException persistenceException){
            return new ResponseEntity<>(new ErrorResponse("User exists",persistenceException.getMessage()),HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        AuthenticationResponse response;
        try {
            response = userService.loginUser(request);
        }
        catch (AuthenticationException authenticationException){
            return new ResponseEntity<>(new ErrorResponse("Wrong credentials",authenticationException.getMessage()),HttpStatus.FORBIDDEN);
        }catch (Exception exception){
            return new ResponseEntity<>(new ErrorResponse("User exists",exception.getMessage()),HttpStatus.LOCKED);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token){
        userService.logout(token);
        return ResponseEntity.ok("Logout successful");
    }
    @PostMapping("/auth/forgotten-password")
    public ResponseEntity<String> forgottenPassword(String email){
        //TODO
        return ResponseEntity.ok("Logout successful");
    }
}
