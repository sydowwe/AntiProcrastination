package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
public class HistoryRequest {
    private Long activityId;
    private Long length;
    private String timeOfStart;
}
