package com.timeOrganizer.model.dto.response.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BaseToDoListResponse extends IdResponse{
    @JsonProperty(value = "isDone")
    private boolean isDone;
    private ActivityResponse activity;
}
