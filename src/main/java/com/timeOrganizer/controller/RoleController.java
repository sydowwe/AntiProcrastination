package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.extendable.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import com.timeOrganizer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@JsonRequestMapping("/role")
@RequiredArgsConstructor
public class RoleController extends MyController {
    private final RoleService roleService;
    @PostMapping("/get-all-options")
    public ResponseEntity<List<SelectOptionResponse>> getAllOptions() {
        return ResponseEntity.ok(mapToSelectOptionResponseList(roleService.getAll(this.getLoggedUser().getId())));
    }
    @PostMapping("/get-by-view/{viewName}")
    public ResponseEntity<Long> getByView(@PathVariable String viewName) {
        return ResponseEntity.ok(roleService.getRoleByView(viewName,this.getLoggedUser().getId()));
    }
    @PostMapping("/get-options-by-category/{categoryId}")
    public ResponseEntity<List<SelectOptionResponse>> getByCategoryId(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapToSelectOptionResponseList(this.roleService.getRolesByCategory(categoryId,this.getLoggedUser().getId())));
    }
    @PostMapping("/create")
    public ResponseEntity<SelectOptionResponse> createNewRole(@RequestBody NameTextColorIconRequest newRole) {
        SelectOptionResponse roleOption = mapToSelectOptionResponse(roleService.insert(newRole, getLoggedUser().getReference()));
        URI uri = this.getCreatedResourceURI(roleOption.getId());
        return ResponseEntity.created(uri).body(roleOption);
    }

}
