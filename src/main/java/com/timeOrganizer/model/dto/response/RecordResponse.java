package com.timeOrganizer.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordResponse {
    private Long id;
    private String activityName;
    private Long activityId;
    private Long length;
    private String timeOfStart;
}
