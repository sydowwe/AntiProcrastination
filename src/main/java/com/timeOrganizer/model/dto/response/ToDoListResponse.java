package com.timeOrganizer.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToDoListResponse implements IResponse{
    private long id;
    private String name;
    private String text;
    private UrgencyResponse urgency;
    @JsonProperty("isDone")
    private boolean isDone;
}
