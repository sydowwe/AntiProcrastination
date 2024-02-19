package com.timeOrganizer.model.dto.request.user;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class EmailRequest implements IRequest {
    private String email;
}
