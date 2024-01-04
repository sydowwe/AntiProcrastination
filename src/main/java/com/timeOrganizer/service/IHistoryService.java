package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.HistoryFilterRequest;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.entity.History;

import java.util.List;

public interface IHistoryService {
    void deleteHistoryById(Long id);
    History updateHistoryById(Long id,HistoryRequest request);
    History getHistoryById(Long id);
    List<History> getAllRecords();

    History addActivityToHistory(HistoryRequest historyRequest);

    List<History> filter(HistoryFilterRequest filterRequest);
}
