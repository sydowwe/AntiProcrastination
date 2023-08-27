package com.timeOrganizer.controller;

import com.timeOrganizer.model.entity.Activity;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity")
public class ActivityController extends MyController{
    private final ActivityService activityService;
    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdNameResponse>> getAllActivities(){
        return new ResponseEntity<>(mapToIdNameResponse(activityService.getAllActivities()), HttpStatus.OK);
    }
    @PostMapping("/get-by-activity")
    public ResponseEntity<Map<String,Long>> getActivityById(@RequestBody Long id) {
        Activity activity = activityService.getActivityById(id);
        Map<String, Long> responseData = new HashMap<>();
        responseData.put("activityId", activity.getId());
        responseData.put("roleId", activity.getRole().getId());
        responseData.put("categoryId", activity.getCategory().getId());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IdNameResponse> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(new IdNameResponse(activityService.getActivityById(id)), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewActivity(@RequestBody ActivityRequest activityRequest){
        URI uri;
        try {
            Activity newActivity = activityService.createActivity(activityRequest);
            uri = new URI("/activity/" + newActivity.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
}
