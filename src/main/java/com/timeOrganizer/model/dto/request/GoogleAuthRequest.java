package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthRequest extends IdRequest {
    private String code;
}

