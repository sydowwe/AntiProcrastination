package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.response.history.HistoryListGroupedByDateResponse;

import java.util.List;

public interface IHistoryService {
	List<HistoryListGroupedByDateResponse> filter(HistoryFilterRequest filterRequest);
}
