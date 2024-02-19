package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.response.history.HistoryResponse;

import java.util.List;

public interface IHistoryService {
    List<HistoryResponse> filter(HistoryFilterRequest filterRequest);
}
