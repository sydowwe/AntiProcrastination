package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ActivityRequest implements IRequest{
    private String name;
    private String text;
    private Boolean isOnToDoList;
    private boolean isUnavoidable;
    private long roleId;
    private long categoryId;
}
