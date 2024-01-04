package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.HistoryMapper;
import com.timeOrganizer.model.dto.request.HistoryFilterRequest;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController extends MyController {
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;

    @PostMapping("/add-new-record")
    public ResponseEntity<?> addNewActivityToHistory(@RequestBody HistoryRequest newRecordRequest) {
        URI uri;
        try {
            var record = historyService.addActivityToHistory(newRecordRequest);
            uri = new URI("/history/" + record.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/get-all")
    public ResponseEntity<List<HistoryResponse>> getWholeHistory() {
        List<History> historyList = historyService.getAllRecords();
        List<HistoryResponse> historyResponseList = historyMapper.convertToFullResponseList(historyList);
        return new ResponseEntity<>(historyResponseList, HttpStatus.OK);
    }
    @PostMapping("/filter")
    public ResponseEntity<List<HistoryResponse>> filterHistory(@RequestBody HistoryFilterRequest filterData) {
        List<History> historyList = historyService.filter(filterData);
        List<HistoryResponse> historyResponseList = historyMapper.convertToFullResponseList(historyList);
        return new ResponseEntity<>(historyResponseList, HttpStatus.OK);
    }
}
