package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.extendable.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
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
    public ResponseEntity<List<SelectOptionResponse>> getAllOptions(){
	    return ResponseEntity.ok(mapToSelectOptionResponseList(categoryService.getAllAsResponse()));
    }
    @PostMapping("/get-options-by-role/{roleId}")
    public ResponseEntity<List<SelectOptionResponse>> getByRole(@PathVariable @NonNull long roleId) {
	    return ResponseEntity.ok(mapToSelectOptionResponseList(categoryService.getCategoriesByRoleId(roleId)));
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewCategory(@RequestBody NameTextColorIconRequest newCategory){
	    SelectOptionResponse categoryOption = mapToSelectOptionResponse(categoryService.insert(newCategory));

        return ResponseEntity.created(this.getCreatedResourceURI(categoryOption.getId()))
                .body(categoryOption);
    }

}
