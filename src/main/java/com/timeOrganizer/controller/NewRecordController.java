package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.CategoryService;
import com.timeOrganizer.service.HistoryService;
import com.timeOrganizer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/newRecord")
public class NewRecordController {
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final ActivityService activityService;
    private final HistoryService historyService;

    @Autowired
    public NewRecordController(RoleService roleService, CategoryService categoryService, ActivityService activityService, HistoryService historyService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.historyService = historyService;
    }
    @GetMapping("/timer")
    public String getTimer(){
        return "newRecord/timer";
    }
    @GetMapping("/stopwatch")
    public String getStopwatch(){
        return "newRecord/stopwatch";
    }
    @GetMapping("/alarm")
    public String getAlarm(){
        return "newRecord/alarm";
    }

    @PostMapping("/get-by-role")
    public ResponseEntity<Map<String, List<IdNameResponse>>> getByRole(@RequestBody Long id) {
        Map<String, List<IdNameResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(roleService.getActivitiesByRole(id)));
        responseData.put("categories", mapToIdNameResponse(getCategoriesByRole(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @PostMapping("/get-by-category")
    public ResponseEntity<Map<String, List<IdNameResponse>>> getByCategory(@RequestBody Long id) {
        Map<String, List<IdNameResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(categoryService.getActivitiesByCategory(id)));
        responseData.put("roles", mapToIdNameResponse(getRolesByCategory(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
    @GetMapping("/role-get-all")
    public ResponseEntity<List<IdNameResponse>> getAllRoles(){
        return new ResponseEntity<>(mapToIdNameResponse(roleService.getAllRoles()), HttpStatus.OK);
    }
    @GetMapping("/category-get-all")
    public ResponseEntity<List<IdNameResponse>> getAllCategories(){
        return new ResponseEntity<>(mapToIdNameResponse(categoryService.getAllCategories()), HttpStatus.OK);
    }
    @GetMapping("/activity-get-all")
    public ResponseEntity<List<IdNameResponse>> getAllActivities(){
        return new ResponseEntity<>(mapToIdNameResponse(activityService.getAllActivities()), HttpStatus.OK);
    }
    @PostMapping("/create-new-activity")
    public ResponseEntity<?> createNewActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.ok().build();
    }
    @PostMapping("/add-new-activity-to-history")
    public ResponseEntity<?> addNewActivityToHistory(@RequestBody HistoryRequest historyRequest){
        return ResponseEntity.ok().build();
    }

    public List<IdNameResponse> mapToIdNameResponse(List<? extends AbstractEntity> list) {
        return list.stream()
                .map(IdNameResponse::new)
                .collect(Collectors.toList());
    }

    public List<Role> getRolesByCategory(Long id){
        List<Role> roles = new ArrayList<>();
        categoryService.getActivitiesByCategory(id).forEach(activity -> {
            if(!roles.contains(activity.getRole())){
                roles.add(activity.getRole());
            }
        });
        return roles;
    }
    public List<Category> getCategoriesByRole(Long id){
        List<Category> categories = new ArrayList<>();
        roleService.getActivitiesByRole(id).forEach(activity -> {
            if(!categories.contains(activity.getCategory())){
                categories.add(activity.getCategory());
            }
        });
        return categories;
    }
}