package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.model.dto.request.extendable.NameTextColorRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class PlannerTaskRequest extends NameTextColorRequest {
    private String startTimestamp;
    private int minuteLength;
    private Long activityId;
}
