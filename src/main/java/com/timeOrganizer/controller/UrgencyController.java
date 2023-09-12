package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.UrgencyMapper;
import com.timeOrganizer.model.dto.response.UrgencyResponse;
import com.timeOrganizer.service.UrgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/urgency")
public class UrgencyController extends MyController {
    private final UrgencyService urgencyService;
    private final UrgencyMapper urgencyMapper;
    @Autowired
    public UrgencyController(UrgencyService urgencyService) {
        this.urgencyService = urgencyService;
        this.urgencyMapper = new UrgencyMapper();
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<UrgencyResponse>> getAllUrgencyItems() {
        List<UrgencyResponse> urgencyResponseList = urgencyService.getAllUrgencyItems().stream().map(urgencyMapper::convertToFullResponse).toList();
        return new ResponseEntity<>(urgencyResponseList, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UrgencyResponse> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(urgencyMapper.convertToFullResponse(urgencyService.getUrgencyItemById(id)), HttpStatus.OK);
    }
}