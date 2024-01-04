package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthRequest {
    private long id;
    private String code;
}

