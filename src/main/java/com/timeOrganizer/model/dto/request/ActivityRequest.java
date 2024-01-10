package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ActivityRequest implements IRequest{
    private String activity;
    private String description;
    private Boolean isOnToDoList;
    private Boolean isObligatory;
    private long roleId;
    private long categoryId;
}
