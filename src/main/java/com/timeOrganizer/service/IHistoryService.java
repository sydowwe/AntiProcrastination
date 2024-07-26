package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.response.history.HistoryListGroupedByDateResponse;
import com.timeOrganizer.security.LoggedUser;

import java.util.List;

public interface IHistoryService {
    List<HistoryListGroupedByDateResponse> filter(LoggedUser user, HistoryFilterRequest filterRequest);
}
