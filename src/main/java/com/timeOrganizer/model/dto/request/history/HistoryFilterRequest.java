package com.timeOrganizer.model.dto.request.history;

import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class HistoryFilterRequest extends HistoryFilterSelectsRequest
{
    private Instant dateFrom;
    private Instant dateTo;
    private Long hoursBack;
}
