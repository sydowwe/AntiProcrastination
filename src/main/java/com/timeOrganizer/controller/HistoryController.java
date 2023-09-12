package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.HistoryMapper;
import com.timeOrganizer.model.dto.request.DateAndHoursRequest;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController extends MyController{
    private final HistoryService historyService;
    private final HistoryMapper historyMapper;
    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
        this.historyMapper = new HistoryMapper();
    }
    @PostMapping("/add-new-record")
    public ResponseEntity<?> addNewActivityToHistory(@RequestBody HistoryRequest newRecordRequest){
        URI uri;
        try {
            var record = historyService.addActivityToHistory(newRecordRequest);
            uri = new URI("/history/" + record.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
    @PostMapping("/get-default")
    public ResponseEntity<List<HistoryResponse>> getWholeHistory(){
        List<HistoryResponse> historyResponseList = historyService.getAllRecords().stream().map(historyMapper::convertToFullResponse).toList();
        return new ResponseEntity<>(historyResponseList , HttpStatus.OK);
    }
    @PostMapping("/get-last-x-hours-records")
    public ResponseEntity<List<HistoryResponse>> getHistoryByDay(@RequestBody DateAndHoursRequest data){
        ZonedDateTime start = ZonedDateTime.parse(data.getDate());
        List<HistoryResponse> historyResponseList = historyService.getLastXHoursRecords(start, data.getHours()).stream().map(historyMapper::convertToFullResponse).toList();
        return new ResponseEntity<>(historyResponseList , HttpStatus.OK);
    }
}
