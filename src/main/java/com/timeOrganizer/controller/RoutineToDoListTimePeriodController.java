package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.toDoList.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.model.dto.response.toDoList.TimePeriodResponse;
import com.timeOrganizer.service.RoutineToDoListTimePeriodTimePeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/routine-to-do-list-time-period")
@RequiredArgsConstructor
public class RoutineToDoListTimePeriodController extends MyController {
    private final RoutineToDoListTimePeriodTimePeriodService timePeriodService;
    @PostMapping("/get-all")
    public ResponseEntity<List<TimePeriodResponse>> getAllUrgencyItems() {
        var test = timePeriodService.getAllAsResponse();
        return ResponseEntity.ok(test);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TimePeriodResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(timePeriodService.getResponseById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<TimePeriodResponse> edit(@PathVariable("id") Long id, @RequestBody TimePeriodRequest request) {
        return ResponseEntity.ok(timePeriodService.updateById(id,request));
    }
    @PostMapping("/change-is-hidden/{id}")
    public ResponseEntity<SuccessResponse> edit(@PathVariable("id") Long id) {
        timePeriodService.changeIsHiddenInView(id);
        return ResponseEntity.ok(new SuccessResponse("hidden changed"));
    }
}