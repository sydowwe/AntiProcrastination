package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ForgotPasswordRequest {
    private String email;
    private String code;
}
