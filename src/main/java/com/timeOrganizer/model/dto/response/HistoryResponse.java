package com.timeOrganizer.model.dto.response;

import lombok.Data;
@Data
public class HistoryResponse {
    private long id;
    private ActivityResponse activity;
    private String startTimestamp;
    private long length;
}
