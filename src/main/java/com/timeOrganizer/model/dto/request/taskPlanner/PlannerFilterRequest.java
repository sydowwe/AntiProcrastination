package com.timeOrganizer.model.dto.request.taskPlanner;

import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class PlannerFilterRequest {
	private Instant filterDate;
	private int hourSpan;
}
