package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.helper.MyIntTime;
import lombok.Getter;

@Getter
public class HistoryRequest {
    private Long activityId;
    private String date;
    private MyIntTime timeOfStart;
    private MyIntTime length;
}
