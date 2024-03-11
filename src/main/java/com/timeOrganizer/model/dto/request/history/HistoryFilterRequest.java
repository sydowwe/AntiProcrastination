package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class HistoryFilterRequest implements IRequest {
    private Long activityId;
    private Long roleId;
    private Long categoryId;
    private Boolean isFromToDoList;
    private Boolean isUnavoidable;
    private Instant dateFrom;
    private Instant dateTo;
    private Long hoursBack;
}
