package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class LoginRequest extends EmailRequest{
    private String password;
    private boolean stayLoggedIn;
}
