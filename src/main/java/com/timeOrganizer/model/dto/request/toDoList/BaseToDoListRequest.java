package com.timeOrganizer.model.dto.request.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class BaseToDoListRequest implements IRequest{
    @JsonProperty(value = "isDone")
    private boolean isDone = false;
    private long activityId;
}
