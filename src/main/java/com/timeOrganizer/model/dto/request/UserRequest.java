package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class UserRequest {
    private String name;
    private String surname;
    private String email;
    private boolean has2FA;
}
