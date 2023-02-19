package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.response.RecordResponse;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.CategoryService;
import com.timeOrganizer.service.HistoryService;
import com.timeOrganizer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewHistoryController {
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final ActivityService activityService;
    private final HistoryService historyService;
    @Autowired
    public ViewHistoryController(RoleService roleService, CategoryService categoryService, ActivityService activityService, HistoryService historyService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.historyService = historyService;
    }

    @GetMapping("/get-full-history")
    public ResponseEntity<List<RecordResponse>> getWholeHistory(){
        return new ResponseEntity<>(new ArrayList<>()   , HttpStatus.OK);
    }
    @PostMapping("/get-history-by-day")
    public ResponseEntity<List<RecordResponse>> getHistoryByDay(@RequestParam LocalDate day){
        return new ResponseEntity<>(new ArrayList<>()   , HttpStatus.OK);
    }
}
