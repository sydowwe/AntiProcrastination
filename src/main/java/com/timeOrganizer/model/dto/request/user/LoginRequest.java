package com.timeOrganizer.model.dto.request.user;

import lombok.Getter;

import java.time.ZoneId;

@Getter
@SuppressWarnings("unused")
public class LoginRequest extends EmailRequest{
    private String password;
    private boolean stayLoggedIn;
    private String recaptchaToken;
    private ZoneId timezone;
}
