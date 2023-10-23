package com.timeOrganizer.model.dto.request;
import lombok.Getter;

@Getter
public class TwoFactorAuthRequest {
    private String email;
    private int code;
}
