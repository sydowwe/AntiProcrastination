package com.timeOrganizer.model.dto.request.taskPlanner;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class PlannerTaskRequest implements IRequest
{
	private Instant startTimestamp;
    private int minuteLength;
	private long activityId;
    private boolean isDone;
}
