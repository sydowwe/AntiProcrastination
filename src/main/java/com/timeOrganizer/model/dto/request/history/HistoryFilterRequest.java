package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.model.dto.request.IRequest;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@SuppressWarnings("unused")
public class HistoryFilterRequest implements IRequest {
    private Long activityId;
    private Long roleId;
    private Long categoryId;
    private Boolean isFromToDoList;
    private Boolean isUnavoidable;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;
    private Long hoursBack;
}
