package com.timeOrganizer.model.dto.request.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class IdIsDoneRequest extends IdRequest {
    @JsonProperty(value = "isDone")
    private boolean isDone;
}
