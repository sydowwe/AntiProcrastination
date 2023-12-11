package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthRequest {
    private String email;
    private String code;
    private boolean stayLoggedIn;
}
