package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/history")
public class HistoryController {
    private final HistoryService historyService;
    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    @PostMapping("/add-activity")
    public ResponseEntity<?> addNewActivityToHistory(@RequestBody HistoryRequest historyRequest){
        URI uri;
        try {
            var record = historyService.addActivityToHistory(historyRequest);
            uri = new URI("/history/" + record.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
   /* @GetMapping("/get-full")
    public ResponseEntity<List<RecordResponse>> getWholeHistory(){
        return new ResponseEntity<>(new ArrayList<>()   , HttpStatus.OK);
    }
    @PostMapping("/get-by-day")
    public ResponseEntity<List<RecordResponse>> getHistoryByDay(@RequestParam LocalDate day){
        return new ResponseEntity<>(new ArrayList<>()   , HttpStatus.OK);
    }*/
}
