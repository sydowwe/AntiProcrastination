package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.RegistrationResponse;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    public ResponseEntity<String> registerUser(RegistrationRequest registration) {
        // Validate registrationDTO and check if the username or email already exists
        // Generate secret key for Google Authenticator

        User newUser = User.builder()
                .name(registration.getName())
                .surname(registration.getSurname())
                .username(registration.getUsername())
                .email(registration.getEmail())
                /*.password(passwordEncoder.encode(registration.getPassword()))
                .secretKey(generateGoogleAuthKey())*/
                .build();


        userRepository.save(newUser);

        // Return a success response or JWT token
        // You can use JWTUtil to generate JWT tokens
        return ResponseEntity.ok("Registration successful");
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        // Validate loginDTO and authenticate the user
        // You can use Spring Security for authentication

        // If authentication is successful, generate a JWT token
        String jwtToken = generateJwtToken(loginRequest.getUsernameOrEmail());

        // Return the JWT token
        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    private String generateJwtToken(String username) {
        // Use your preferred method or library to generate JWT tokens
        // Example: JWTUtil.generateToken(username)
        return "your_generated_jwt_token";
    }

    private String generateGoogleAuthKey() {
        // Implement this method to generate a secret key for Google Authenticator
        // Example: use a library like java.util.UUID.randomUUID().toString()
        return "your_generated_secret_key";
    }
}