package com.timeOrganizer.security.twoFactorAuth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.warrenstrange.googleauth.GoogleAuthenticator;

@Configuration
public class CustomGoogleAuthenticatorConfig {
    private final TwoFactorAuthRepository twoFactorAuthRepository;
    @Autowired
    public CustomGoogleAuthenticatorConfig(TwoFactorAuthRepository twoFactorAuthRepository) {
        this.twoFactorAuthRepository = twoFactorAuthRepository;
    }
    @Bean
    public GoogleAuthenticator gAuth() {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        googleAuthenticator.setCredentialRepository(twoFactorAuthRepository);
        return googleAuthenticator;
    }
}