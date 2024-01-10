package com.timeOrganizer.model.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class LoginResponse extends IdResponse {
    private String email;
    private String token;
    private boolean has2FA;
}
