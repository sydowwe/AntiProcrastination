package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
        return ResponseEntity.ok(mapToIdNameResponse(roleService.getAllRoles()));
    }
    @PostMapping("/get-by-category/{categoryId}")
    public ResponseEntity<List<IdLabelResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapToIdNameResponse(getRolesByCategory(categoryId)));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewRole(@RequestBody NameTextColorIconRequest newRole){
        Role role;
        URI uri;
        try {
            role = roleService.createRole(newRole);
            uri = new URI("/category/" + role.getId());

        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).body(role);
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
