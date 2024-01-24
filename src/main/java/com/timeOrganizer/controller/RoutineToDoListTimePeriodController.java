package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.TimePeriodResponse;
import com.timeOrganizer.service.RoutineToDoListTimePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/routine-to-do-list-time-period")
@RequiredArgsConstructor
public class RoutineToDoListTimePeriodController extends MyController {
    private final RoutineToDoListTimePeriodService timePeriodService;
    @PostMapping("/get-all")
    public ResponseEntity<List<TimePeriodResponse>> getAllUrgencyItems() {
        var test = timePeriodService.getAll(getLoggedUser().getId());
        return ResponseEntity.ok(test);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TimePeriodResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(timePeriodService.getResponseById(id));
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<TimePeriodResponse> edit(@PathVariable("id") Long id, @RequestBody TimePeriodRequest request) {
        return ResponseEntity.ok(timePeriodService.updateById(id,request));
    }
}