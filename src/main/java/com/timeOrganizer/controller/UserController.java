package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.AuthenticationResponse;
import com.timeOrganizer.model.dto.response.ErrorResponse;
import com.timeOrganizer.service.UserService;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(userService.loginUser(request));
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){

        return "redirect:/login";
    }
}
