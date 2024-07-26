package com.timeOrganizer.model.dto.request.taskPlanner;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class PlannerTaskRequest implements IRequest
{
    private String startTimestamp;
    private int minuteLength;
    private Long activityId;
    private boolean isDone;
}
