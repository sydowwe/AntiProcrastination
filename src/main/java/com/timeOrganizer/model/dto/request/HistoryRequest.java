package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.helper.MyIntTime;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class HistoryRequest {
    private Long activityId;
    private String date;
    private LocalDateTime start;
    private MyIntTime length;
}
