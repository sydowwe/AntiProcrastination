package com.timeOrganizer.model.dto.request.taskPlanner;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class PlannerFilterRequest {
	private String filterDate;
	private int hourSpan;
}
