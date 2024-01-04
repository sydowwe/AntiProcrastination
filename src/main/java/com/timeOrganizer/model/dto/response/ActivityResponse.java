package com.timeOrganizer.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityResponse {
    private Long id;
    private String name;
    private String text;
    private boolean isOnToDoList;
    private boolean isUnavoidable;
    private RoleResponse role;
    private CategoryResponse category;
}