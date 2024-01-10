package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.HistoryFilterRequest;
import com.timeOrganizer.model.dto.response.HistoryResponse;

import java.util.List;

public interface IHistoryService {
    List<HistoryResponse> filter(HistoryFilterRequest filterRequest);
}
