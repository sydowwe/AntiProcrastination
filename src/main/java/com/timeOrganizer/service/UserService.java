package com.timeOrganizer.service;

import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.model.dto.request.LoginRequest;
import com.timeOrganizer.model.dto.request.RegistrationRequest;
import com.timeOrganizer.model.dto.response.AuthenticationResponse;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUserRepository;
import com.timeOrganizer.security.UserRole;
import com.timeOrganizer.security.config.JwtService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegistrationRequest registration) throws PersistenceException {
        User newUser = User.builder()
                .name(registration.getName())
                .surname(registration.getSurname())
//                .username(registration.getUsername())
                .email(registration.getEmail())
                .password(new BCryptPasswordEncoder().encode(registration.getPassword()))
                .role(UserRole.USER)
                .build();
        if (registration.isUse2FA()) {
            newUser.setSecretKey2FA(generateGoogleAuthKey());
        }
        var jwtToken = jwtService.generateToken(newUser);
        try {
            userRepository.save(newUser);
        }catch (Exception e){
            throw new PersistenceException(e);
        }
        return AuthenticationResponse.builder().token(jwtToken).email(newUser.getEmail()).build();
    }

    private String generateGoogleAuthKey() {
        return java.util.UUID.randomUUID().toString();
    }

    public AuthenticationResponse loginUser(LoginRequest loginRequest) throws UserNotFoundException, AuthenticationException {
        User user = this.findByEmail(loginRequest.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

}