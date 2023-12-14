package com.timeOrganizer.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ActivityResponse {
    private Long id;
    private String name;
    private String text;
    @JsonProperty("isOnToDoList")
    private boolean isOnToDoList;
    @JsonProperty("isUnavoidable")
    private boolean isUnavoidable;
    private RoleResponse role;
    private CategoryResponse category;
}