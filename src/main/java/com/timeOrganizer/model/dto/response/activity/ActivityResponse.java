package com.timeOrganizer.model.dto.response.activity;

import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.dto.response.extendable.NameTextResponse;
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