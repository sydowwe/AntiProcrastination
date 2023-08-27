package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
public class ActivityRequest {
    private String name;
    private String text;
    private Boolean isOnToDoList;
    private Boolean isNecessary;
    private Long roleId;
    private String newRoleName;
    private String newRoleText;
    private Long categoryId;
    private String newCategoryName;
    private String newCategoryText;
}
