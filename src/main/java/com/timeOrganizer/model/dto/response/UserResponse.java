package com.timeOrganizer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse implements IResponse{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private boolean has2FA;
}
