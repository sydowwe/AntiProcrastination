package com.timeOrganizer.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoListResponse implements IResponse{
    private long id;
    private String name;
    private String text;
    private UrgencyResponse urgency;
    private boolean isDone;
}
