package com.timeOrganizer.model.dto.request.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.request.extendable.ActivityIdRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class BaseToDoListRequest extends ActivityIdRequest
{
    @JsonProperty(value = "isDone")
    private boolean isDone = false;
}
