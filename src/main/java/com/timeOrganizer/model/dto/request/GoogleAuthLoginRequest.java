package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthLoginRequest {
    private String email;
    private String code;
}
