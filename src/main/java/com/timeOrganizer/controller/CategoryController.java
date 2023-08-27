package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.model.entity.Category;
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
@RequestMapping("/category")
public class CategoryController extends MyController{
    private final CategoryService categoryService;
    private final RoleService roleService;

    @Autowired
    public CategoryController(CategoryService categoryService, RoleService roleService) {
        this.categoryService = categoryService;
        this.roleService = roleService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdNameResponse>> getAllCategories(){
        return new ResponseEntity<>(mapToIdNameResponse(categoryService.getAllCategories()), HttpStatus.OK);
    }
    @PostMapping("/get-by-role")
    public ResponseEntity<Map<String, List<IdNameResponse>>> getByRole(@RequestBody Long id) {
        Map<String, List<IdNameResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(roleService.getActivitiesByRole(id)));
        responseData.put("categories", mapToIdNameResponse(getCategoriesByRole(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
