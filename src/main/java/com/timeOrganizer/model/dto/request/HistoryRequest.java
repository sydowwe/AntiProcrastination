package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.helper.MyIntTime;
import lombok.Getter;

@Getter
public class HistoryRequest {
    private Long activityId;
    private String startTimestamp;
    private MyIntTime length;
}
