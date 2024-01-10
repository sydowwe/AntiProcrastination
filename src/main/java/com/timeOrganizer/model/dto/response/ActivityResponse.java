package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ActivityResponse extends NameTextResponse {
    private boolean isOnToDoList;
    private boolean isUnavoidable;
    private RoleResponse role;
    private CategoryResponse category;
}