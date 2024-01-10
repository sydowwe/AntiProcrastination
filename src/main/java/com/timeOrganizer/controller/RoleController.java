package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
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
    public ResponseEntity<List<IdLabelResponse>> getAllOptions() {
        return ResponseEntity.ok(mapToIdNameResponseList(roleService.getAll(this.getLoggedUser().getId())));
    }
    @PostMapping("/get-options-by-category/{categoryId}")
    public ResponseEntity<List<IdLabelResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapToIdNameResponseList(this.roleService.getRolesByCategory(categoryId,this.getLoggedUser().getId())));
    }
    @PostMapping("/create")
    public ResponseEntity<IdLabelResponse> createNewRole(@RequestBody NameTextColorIconRequest newRole) {
        IdLabelResponse roleOption = mapToIdNameResponse(roleService.insert(newRole, getLoggedUser().getId()));
        URI uri = this.getCreatedResourceURI(roleOption.getId());
        return ResponseEntity.created(uri).body(roleOption);
    }

}
