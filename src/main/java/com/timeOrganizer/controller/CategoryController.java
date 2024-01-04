package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.service.ActivityService;
import com.timeOrganizer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController extends MyController{
    private final CategoryService categoryService;
    private final ActivityService activityService;
    @PostMapping("/get-all-options")
    public ResponseEntity<List<IdLabelResponse>> getAllOptions(){
        return ResponseEntity.ok(mapToIdNameResponseList(categoryService.getAllCategories()));
    }
    @PostMapping("/get-options-by-role/{roleId}")
    public ResponseEntity<List<IdLabelResponse>> getByRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(mapToIdNameResponseList(getCategoriesByRole(roleId)));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody NameTextColorIconRequest newCategory){
        IdLabelResponse categoryOption;
        URI uri;
        try {
            categoryOption = mapToIdNameResponse(categoryService.createCategory(newCategory));
            uri = new URI("/category/" + categoryOption.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating activity: " + e.getMessage());
        }
        return ResponseEntity.created(uri).body(categoryOption);
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
