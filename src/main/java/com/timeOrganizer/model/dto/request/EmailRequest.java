package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class EmailRequest implements IRequest{
    private String email;
}
