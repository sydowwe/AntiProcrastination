package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.NewActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController extends MyController{
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    @PostMapping("/get-all-options")
    public ResponseEntity<List<IdLabelResponse>> getAllActivities(){
        return new ResponseEntity<>(mapToIdNameResponseList(activityService.getAllActivities()), HttpStatus.OK);
    }
    @PostMapping("/get-by-activity")
    public ResponseEntity<ActivityResponse> getActivityById(@RequestBody Long id) {
        Activity activity = activityService.getActivityById(id);
        return new ResponseEntity<>(activityMapper.convertToFullResponse(activity), HttpStatus.OK);
    }
    @PostMapping("/get-options-by-role/{roleId}")
    public ResponseEntity<List<IdLabelResponse>> getActivitiesByRoleId(@PathVariable Long roleId) {
        List<Activity> activities = activityService.getActivitiesByRoleId(roleId);
        return new ResponseEntity<>(activities.stream().map(IdLabelResponse::new).toList(), HttpStatus.OK);
    }
    @PostMapping("/get-options-by-category/{categoryId}")
    public ResponseEntity<List<IdLabelResponse>> getActivitiesByCategoryId(@PathVariable Long categoryId) {
        List<Activity> activities = activityService.getActivitiesByCategoryId(categoryId);
        return new ResponseEntity<>(activities.stream().map(IdLabelResponse::new).toList(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(new IdLabelResponse(activityService.getActivityById(id)), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewActivity(@RequestBody NewActivityRequest formData){
        URI uri;
        try {
            Activity newActivity = activityService.createActivity(formData);
            uri = new URI("/activity/" + newActivity.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
}
