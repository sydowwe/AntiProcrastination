package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@JsonRequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController extends MyController{
    private final CategoryService categoryService;
    @PostMapping("/get-all-options")
    public ResponseEntity<List<IdLabelResponse>> getAllOptions(){
        return ResponseEntity.ok(mapToIdNameResponseList(categoryService.getAll(getLoggedUser().getId())));
    }
    @PostMapping("/get-options-by-role/{roleId}")
    public ResponseEntity<List<IdLabelResponse>> getByRole(@PathVariable @NonNull long roleId) {
        return ResponseEntity.ok(mapToIdNameResponseList(categoryService.getCategoriesByRole(roleId, getLoggedUser().getId())));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody NameTextColorIconRequest newCategory){
        IdLabelResponse categoryOption = mapToIdNameResponse(categoryService.insert(newCategory,this.getLoggedUser().getId()));

        return ResponseEntity.created(this.getCreatedResourceURI(categoryOption.getId()))
                .body(categoryOption);
    }

}
