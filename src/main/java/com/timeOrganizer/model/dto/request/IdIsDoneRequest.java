package com.timeOrganizer.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class IdIsDoneRequest extends IdRequest{
    @JsonProperty(value = "isDone")
    private boolean isDone;
}
