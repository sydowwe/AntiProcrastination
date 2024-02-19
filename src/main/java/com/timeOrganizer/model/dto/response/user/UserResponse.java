package com.timeOrganizer.model.dto.response.user;

import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UserResponse extends IdResponse {
    private String name;
    private String surname;
    private String email;
    private boolean has2FA;
}
