package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
public class NewActivityRequest {
    private String activity;
    private String description;
    private Boolean isOnToDoList;
    private Boolean isObligatory;
    private Long roleId;
    private Long categoryId;
}
