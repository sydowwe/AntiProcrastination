package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController extends MyController{
    private final CategoryService categoryService;
    private final ActivityService activityService;

    @Autowired
    public CategoryController(CategoryService categoryService, ActivityService activityService) {
        this.categoryService = categoryService;
        this.activityService = activityService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdLabelResponse>> getAllCategories(){
        return ResponseEntity.ok(mapToIdNameResponse(categoryService.getAllCategories()));
    }
    @PostMapping("/get-by-role/{roleId}")
    public ResponseEntity<List<IdLabelResponse>> getByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(mapToIdNameResponse(getCategoriesByRole(roleId)));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody NameTextColorIconRequest newCategory){
        Category category;
        URI uri;
        try {
            category = categoryService.createCategory(newCategory);
            uri = new URI("/category/" + category.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).body(category);
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
