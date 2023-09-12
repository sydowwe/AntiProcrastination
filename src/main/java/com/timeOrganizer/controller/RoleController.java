package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController extends MyController{
    private final RoleService roleService;
    private final ActivityService activityService;

    @Autowired
    public RoleController(RoleService roleService, ActivityService activityService) {
        this.roleService = roleService;
        this.activityService = activityService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdLabelResponse>> getAllRoles(){
        return new ResponseEntity<>(mapToIdNameResponse(roleService.getAllRoles()), HttpStatus.OK);
    }
    @PostMapping("/get-by-category")
    public ResponseEntity<Map<String, List<IdLabelResponse>>> getByCategory(@RequestBody Long id) {
        Map<String, List<IdLabelResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(activityService.getActivitiesByCategoryId(id)));
        responseData.put("roles", mapToIdNameResponse(getRolesByCategory(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewRole(@RequestBody NameTextColorIconRequest newRole){
        URI uri;
        try {
            Role role = roleService.createRole(newRole);
            uri = new URI("/role/" + role.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
    public List<Role> getRolesByCategory(Long id){
        List<Role> roles = new ArrayList<>();
        activityService.getActivitiesByCategoryId(id).forEach(activity -> {
            if(!roles.contains(activity.getRole())){
                roles.add(activity.getRole());
            }
        });
        return roles;
    }
}
