package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.IdIsDoneRequest;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.*;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.service.ToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/to-do-list")
@RequiredArgsConstructor
public class ToDoListController extends MyController {
    private final ToDoListService toDoListService;
    @PostMapping("/get-all")
    public ResponseEntity<List<ToDoListResponse>> getAllToDoListItems() {
        return ResponseEntity.ok(toDoListService.getAll(this.getLoggedUser().getId()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.mapToIdNameResponse(toDoListService.getResponseById(id)));
    }
    @PostMapping("/change-done")
    public ResponseEntity<SuccessResponse> changeDone(@RequestBody IdIsDoneRequest request) {
        toDoListService.setIsDone(request);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/batch-change-done")
    public ResponseEntity<SuccessResponse> batchChangeDone(@RequestBody List<IdIsDoneRequest> request) {
        toDoListService.batchSetIsDone(request);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/add")
    public ResponseEntity<ToDoListResponse> addToDoListItem(@RequestBody ToDoListRequest toDoListRequest) {
        ToDoListResponse newToDoListItem = toDoListService.insert(toDoListRequest, this.getLoggedUser().getId());
        return ResponseEntity
                .created(this.getCreatedResourceURI(newToDoListItem.getId()))
                .body(newToDoListItem);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ToDoListResponse> editToDoListItem(@PathVariable("id") Long id, @RequestBody ToDoListRequest toDoListRequest) {
        return ResponseEntity.ok(toDoListService.updateById(id, toDoListRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteToDoListItem(@PathVariable("id") Long id) {
        toDoListService.deleteById(id);
        return ResponseEntity.ok(new IdResponse(id));
    }
}
