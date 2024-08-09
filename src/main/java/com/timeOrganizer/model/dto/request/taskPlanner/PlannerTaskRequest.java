package com.timeOrganizer.model.dto.request.taskPlanner;

import com.timeOrganizer.model.dto.request.extendable.ActivityIdRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class PlannerTaskRequest extends ActivityIdRequest
{
	private Instant startTimestamp;
    private int minuteLength;
    private boolean isDone;
}
