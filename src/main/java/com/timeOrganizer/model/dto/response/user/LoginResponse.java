package com.timeOrganizer.model.dto.response.user;

import com.timeOrganizer.model.dto.response.extendable.IdResponse;
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
