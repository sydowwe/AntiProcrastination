package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.service.RoutineToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/routine-to-do-list")
@RequiredArgsConstructor
public class RoutineToDoListController extends MyController{
    private final RoutineToDoListService routineToDoListService;
    @PostMapping("/get-all")
    public ResponseEntity<List<RoutineToDoListGroupedResponse>> getAllRoutineToDoListItems() {
        return ResponseEntity.ok(routineToDoListService.getAllByUserIdGroupedByTimePeriod());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SelectOptionResponse> get(@PathVariable("id") Long id) {
        var response =  routineToDoListService.getResponseById(id);
        return ResponseEntity.ok(this.mapToSelectOptionResponse(response.getId(),response.getActivity().getName()));
    }
    @PatchMapping("/change-done")
    public ResponseEntity<SuccessResponse> changeDone(@RequestBody List<IdRequest> requestList) {
        routineToDoListService.setIsDone(requestList);
        return ResponseEntity
                .ok(new SuccessResponse("changed"));
    }
    @PostMapping("/add")
    public ResponseEntity<RoutineToDoListResponse> createRoutineToDoListItem(@RequestBody RoutineToDoListRequest routineToDoListRequest) {
        RoutineToDoListResponse newRoutineToDoListItem = routineToDoListService.insert(routineToDoListRequest);
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
    @PostMapping("/batch-delete")
    public ResponseEntity<SuccessResponse> batchDelete(@RequestBody List<IdRequest> request) {
        routineToDoListService.batchDelete(request);
        return ResponseEntity
                .ok(new SuccessResponse("deleted"));
    }
}
