package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.RoleMapper;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController extends MyController{
    private final RoleService roleService;
    private final ActivityService activityService;
    private final RoleMapper roleMapper;

    @PostMapping("/get-all-options")
    public ResponseEntity<List<IdLabelResponse>> getAllOptions(){
        return ResponseEntity.ok(mapToIdNameResponseList(roleService.getAllRoles()));
    }
    @PostMapping("/get-options-by-category/{categoryId}")
    public ResponseEntity<List<IdLabelResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapToIdNameResponseList(getRolesByCategory(categoryId)));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewRole(@RequestBody NameTextColorIconRequest newRole){
        IdLabelResponse roleOption;
        URI uri;
        try {
            roleOption = mapToIdNameResponse(roleService.createRole(newRole));
            uri = new URI("/role/" + roleOption.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).body(roleOption);
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
