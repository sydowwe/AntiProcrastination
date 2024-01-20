package com.timeOrganizer.security.config;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class OAuth2TokenResponse {
    private String accessToken;
    private String tokenType;
    private Integer issuedAt;
    private Integer expiresAt;
    private String refreshToken;
    private String id_token;
    public Instant getIssuedAtInstant(){
        return Instant.ofEpochMilli(issuedAt);
    }
    public Instant getExpiresAtInstant(){
        return Instant.ofEpochMilli(expiresAt);
    }
}
