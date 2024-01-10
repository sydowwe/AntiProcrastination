package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class HistoryResponse extends IdResponse{
    private ActivityResponse activity;
    private String startTimestamp;
    private int length;
}
