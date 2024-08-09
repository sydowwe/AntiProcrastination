package com.timeOrganizer.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.response.extendable.EntityWithActivityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PlannerTaskResponse extends EntityWithActivityResponse
{
    private String startTimestamp;
    private int minuteLength;
    @JsonProperty(value = "isDone")
    private boolean isDone;
}
