package com.timeOrganizer.model.dto.request.user;

import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class GoogleAuthRequest extends IdRequest {
    private String code;
}

