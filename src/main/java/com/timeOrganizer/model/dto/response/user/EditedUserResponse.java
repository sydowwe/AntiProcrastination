package com.timeOrganizer.model.dto.response.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class EditedUserResponse extends UserResponse{
    private String token;
    private byte[] qrCode;
}
