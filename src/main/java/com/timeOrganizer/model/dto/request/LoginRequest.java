package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class LoginRequest {
    private String email;
    private String password;
    private boolean stayLoggedIn;
}
