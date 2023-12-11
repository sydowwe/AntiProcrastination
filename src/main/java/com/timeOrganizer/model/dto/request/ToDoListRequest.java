package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ToDoListRequest {
    private String name;
    private String text;
    private long urgencyId;
    private boolean isDone;
}
