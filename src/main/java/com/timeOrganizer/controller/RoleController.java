package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.service.CategoryService;
import com.timeOrganizer.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController extends MyController{
    private final RoleService roleService;
    private final CategoryService categoryService;

    @Autowired
    public RoleController(RoleService roleService, CategoryService categoryService) {
        this.roleService = roleService;
        this.categoryService = categoryService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdNameResponse>> getAllRoles(){
        return new ResponseEntity<>(mapToIdNameResponse(roleService.getAllRoles()), HttpStatus.OK);
    }
    @PostMapping("/get-by-category")
    public ResponseEntity<Map<String, List<IdNameResponse>>> getByCategory(@RequestBody Long id) {
        Map<String, List<IdNameResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(categoryService.getActivitiesByCategory(id)));
        responseData.put("roles", mapToIdNameResponse(getRolesByCategory(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
}
