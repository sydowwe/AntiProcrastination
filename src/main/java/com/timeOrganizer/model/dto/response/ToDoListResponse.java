package com.timeOrganizer.model.dto.response;

import lombok.Data;

@Data
public class ToDoListResponse {
    private long id;
    private String name;
    private String text;
    private boolean isDone;
    private UrgencyResponse urgency;
}
