package com.timeOrganizer.model.dto.request;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    private boolean isGoogleAuth;
}
