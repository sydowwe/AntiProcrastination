package com.timeOrganizer.model.dto.request.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.request.NameTextRequest;
import lombok.Getter;

@Getter
public class BaseToDoListRequest extends NameTextRequest {
    @JsonProperty(value = "isDone")
    private boolean isDone = false;
}
