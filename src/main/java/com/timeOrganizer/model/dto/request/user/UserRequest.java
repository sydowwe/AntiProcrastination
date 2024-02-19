package com.timeOrganizer.model.dto.request.user;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class UserRequest implements IRequest {
    private String name;
    private String surname;
    private String email;
    private boolean has2FA;
}
