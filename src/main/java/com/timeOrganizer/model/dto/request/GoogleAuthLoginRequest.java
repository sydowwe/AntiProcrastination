package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthLoginRequest extends EmailRequest{
    private String code;
}
