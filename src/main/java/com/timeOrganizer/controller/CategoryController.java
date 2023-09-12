package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.CategoryService;
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
@RequestMapping("/category")
public class CategoryController extends MyController{
    private final CategoryService categoryService;
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ActivityService activityService) {
        this.categoryService = categoryService;
        this.activityService = activityService;
        this.activityMapper = new ActivityMapper();
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdLabelResponse>> getAllCategories(){
        return new ResponseEntity<>(mapToIdNameResponse(categoryService.getAllCategories()), HttpStatus.OK);
    }
    @PostMapping("/get-by-role")
    public ResponseEntity<Map<String, List<IdLabelResponse>>> getByRole(@RequestBody Long id) {
        Map<String, List<IdLabelResponse>> responseData = new HashMap<>();
        responseData.put("activities", mapToIdNameResponse(activityService.getActivitiesByRoleId(id)));
        responseData.put("categories", mapToIdNameResponse(getCategoriesByRole(id)));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody NameTextColorIconRequest newCategory){
        URI uri;
        try {
            Category category = categoryService.createCategory(newCategory);
            uri = new URI("/category/" + category.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }




    public List<Category> getCategoriesByRole(Long id){
        List<Category> categories = new ArrayList<>();
        activityService.getActivitiesByRoleId(id).forEach(activity -> {
            if(!categories.contains(activity.getCategory())){
                categories.add(activity.getCategory());
            }
        });
        return categories;
    }
}
