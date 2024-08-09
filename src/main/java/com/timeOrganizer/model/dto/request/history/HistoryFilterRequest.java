package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.model.dto.request.ActivitySelectForm;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class HistoryFilterRequest extends ActivitySelectForm
{
    private Instant dateFrom;
    private Instant dateTo;
    private Long hoursBack;
}
