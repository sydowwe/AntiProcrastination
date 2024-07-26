package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.model.dto.request.extendable.NameTextColorRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class AlarmRequest extends NameTextColorRequest
{
	private Instant startTimestamp;
	private Long activityId;
	private boolean isActive;
}
