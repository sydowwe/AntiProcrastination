package com.timeOrganizer.model.dto.response;

import lombok.Data;
@Data
public class HistoryResponse {
    private ActivityResponse activity;
    private String startTimestamp;
    private long length;
}
