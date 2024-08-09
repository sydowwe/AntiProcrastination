package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.ActivitySelectForm;
import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.request.history.HistoryRequest;
import com.timeOrganizer.model.dto.response.activity.ActivityFormSelectsResponse;
import com.timeOrganizer.model.dto.response.history.HistoryListGroupedByDateResponse;
import com.timeOrganizer.model.dto.response.history.HistoryResponse;
import com.timeOrganizer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@JsonRequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController extends MyController {
    private final HistoryService historyService;
    @PostMapping("/add-new-record")
    public ResponseEntity<URI> addNewActivityToHistory(@RequestBody HistoryRequest newRecordRequest) {
        var record = historyService.insert(newRecordRequest);
        return ResponseEntity.created(this.getCreatedResourceURI(record.getId())).build();
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<HistoryResponse>> getWholeHistory() {
        return ResponseEntity.ok(historyService.getAllAsResponse());
    }
    @PostMapping("/filter")
    public ResponseEntity<List<HistoryListGroupedByDateResponse>> filterHistory(@RequestBody HistoryFilterRequest filterData)
    {
        return ResponseEntity.ok(historyService.filter(filterData));
    }

    @PostMapping("/update-filter-selects")
    public ResponseEntity<ActivityFormSelectsResponse> updateFilterSelects(@RequestBody ActivitySelectForm filterData)
    {
        return ResponseEntity.ok(historyService.updateFilterSelects(filterData));
    }
}
