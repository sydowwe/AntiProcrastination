package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
public class ToDoListRequest {
    private String name;
    private String text;
    private boolean isDone;
    private long urgencyId;
}
