package com.timeOrganizer.controller;

import com.timeOrganizer.controller.generic.MyController;
import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.service.ToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/to-do-list")
@RequiredArgsConstructor
public class ToDoListController extends MyController
{
    private final ToDoListService toDoListService;
    @PostMapping("/get-all")
    public ResponseEntity<List<ToDoListResponse>> getAllToDoListItems() {
        return ResponseEntity.ok(toDoListService.getAllAsResponse());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SelectOptionResponse> get(@PathVariable("id") Long id) {
        var response = toDoListService.getResponseById(id);
        return ResponseEntity.ok(this.mapToSelectOptionResponse(response.getId(),response.getActivity().getName()));
    }
    @PatchMapping("/change-done")
    public ResponseEntity<SuccessResponse> changeDone(@RequestBody List<IdRequest> requestList) {
        toDoListService.setIsDone(requestList);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/add")
    public ResponseEntity<ToDoListResponse> addToDoListItem(@RequestBody ToDoListRequest toDoListRequest) {
        ToDoListResponse newToDoListItem = toDoListService.insert(toDoListRequest);
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
    @PostMapping("/batch-delete")
    public ResponseEntity<SuccessResponse> batchDelete(@RequestBody List<IdRequest> request) {
        toDoListService.batchDelete(request);
        return ResponseEntity
                .ok(new SuccessResponse("deleted"));
    }
}
