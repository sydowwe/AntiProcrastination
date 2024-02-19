package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.response.toDoList.UrgencyResponse;
import com.timeOrganizer.service.UrgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/urgency")
@RequiredArgsConstructor
public class UrgencyController extends MyController {
    private final UrgencyService urgencyService;
    @PostMapping("/get-all")
    public ResponseEntity<List<UrgencyResponse>> getAllUrgencyItems() {
        var test = urgencyService.getAll(getLoggedUser().getId());
        return ResponseEntity.ok(test);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UrgencyResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(urgencyService.getResponseById(id));
    }
}