package com.timeOrganizer.model.dto.request;

import lombok.Getter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
public class HistoryRequest {
    private Long activityId;
    private Long length;
    private String timeOfStart;
}
