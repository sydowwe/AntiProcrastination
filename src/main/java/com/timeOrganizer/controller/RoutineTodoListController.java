package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.IdIsDoneRequest;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.dto.response.IdResponse;
import com.timeOrganizer.model.dto.response.SuccessResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.service.RoutineToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/routine-to-do-list")
@RequiredArgsConstructor
public class RoutineTodoListController extends MyController{
    private final RoutineToDoListService routineToDoListService;
    @PostMapping("/get-all")
    public ResponseEntity<List<RoutineToDoListResponse>> getAllRoutineToDoListItems() {
        return ResponseEntity.ok(routineToDoListService.getAll(this.getLoggedUser().getId()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.mapToIdNameResponse(routineToDoListService.getResponseById(id)));
    }
    @PostMapping("/change-done")
    public ResponseEntity<SuccessResponse> changeDone(@RequestBody IdIsDoneRequest request) {
        routineToDoListService.setIsDone(request);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/batch-change-done")
    public ResponseEntity<SuccessResponse> batchChangeDone(@RequestBody List<IdIsDoneRequest> request) {
        routineToDoListService.batchSetIsDone(request);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/add")
    public ResponseEntity<RoutineToDoListResponse> addRoutineToDoListItem(@RequestBody RoutineToDoListRequest routineToDoListRequest) {
        RoutineToDoListResponse newRoutineToDoListItem = routineToDoListService.insert(routineToDoListRequest, this.getLoggedUser().getId());
        return ResponseEntity
                .created(this.getCreatedResourceURI(newRoutineToDoListItem.getId()))
                .body(newRoutineToDoListItem);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoutineToDoListResponse> editRoutineToDoListItem(@PathVariable("id") Long id, @RequestBody RoutineToDoListRequest routineToDoListRequest) {
        return ResponseEntity.ok(routineToDoListService.updateById(id, routineToDoListRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> deleteRoutineToDoListItem(@PathVariable("id") Long id) {
        routineToDoListService.deleteById(id);
        return ResponseEntity.ok(new IdResponse(id));
    }
}
