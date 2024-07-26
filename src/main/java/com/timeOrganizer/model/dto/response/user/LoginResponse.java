package com.timeOrganizer.model.dto.response.user;

import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LoginResponse extends IdResponse {
    private String email;
    private String token;
    private boolean has2FA;
    private AvailableLocales currentLocale;
}
