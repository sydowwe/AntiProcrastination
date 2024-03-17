package com.timeOrganizer.model.dto.response.history;

import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class HistoryResponse extends IdResponse {
    private ActivityResponse activity;
    private String startTimestamp;
    private int length;
}
