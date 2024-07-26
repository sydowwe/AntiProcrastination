package com.timeOrganizer.model.dto.request.user;

import com.timeOrganizer.helper.AvailableLocales;
import lombok.Getter;

import java.time.ZoneId;

@Getter
@SuppressWarnings("unused")
public class RegistrationRequest extends UserRequest{
    private String password;
    private String recaptchaToken;
    private AvailableLocales currentLocale;
    private ZoneId timezone;
}
