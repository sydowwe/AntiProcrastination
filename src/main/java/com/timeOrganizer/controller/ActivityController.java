package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@JsonRequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController extends MyController {
    private final ActivityService activityService;

    @PostMapping("/get-all-options")
    public ResponseEntity<List<IdLabelResponse>> getAllActivities() {
        return ResponseEntity.ok(mapToIdNameResponseList(activityService.getAll(this.getLoggedUser().getId())));
    }

    @PostMapping("/get-options-by-role/{roleId}")
    public ResponseEntity<?> getActivitiesByRoleId(@PathVariable Long roleId) {
        if (roleId == null || roleId == 0) {
            return ResponseEntity.badRequest().body("roleId must not be null or 0");
        }
        return ResponseEntity.ok(this.mapToIdNameResponseList(activityService.getActivitiesByRoleId(roleId, this.getLoggedUser().getId())));
    }

    @PostMapping("/get-options-by-category/{categoryId}")
    public ResponseEntity<?> getActivitiesByCategoryId(@PathVariable Long categoryId) {
        if (categoryId == null || categoryId == 0) {
            return ResponseEntity.badRequest().body("categoryId must not be null or 0");
        }
        return ResponseEntity.ok(this.mapToIdNameResponseList(activityService.getActivitiesByCategoryId(categoryId, this.getLoggedUser().getId())));
    }
    //TODO
    @PostMapping("/get-by-activity")
    public ResponseEntity<ActivityResponse> getActivityById(@RequestBody @NonNull Long id) {
        return ResponseEntity.ok(activityService.getResponseById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.mapToIdNameResponse(activityService.getResponseById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewActivity(@RequestBody ActivityRequest formData) {
        ActivityResponse newActivity = activityService.insert(formData, this.getLoggedUser().getReference());
        URI uri = this.getCreatedResourceURI(newActivity.getId());
        return ResponseEntity.created(uri).build();
    }
}
