package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.ToDoListMapper;
import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.dto.response.IdLabelResponse;
import com.timeOrganizer.model.dto.response.ToDoListResponse;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/toDoList")
public class ToDoListController extends MyController {
    private final ToDoListService toDoListService;
    private final ToDoListMapper toDoListMapper;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
        this.toDoListMapper = new ToDoListMapper();
    }

    @PostMapping("/get-all")
    public ResponseEntity<List<ToDoListResponse>> getAllToDoListItems() {
        List<ToDoListResponse> toDoListResponseList = toDoListService.getAllToDoListItems().stream().map(toDoListMapper::convertToFullResponse).toList();
        return new ResponseEntity<>(toDoListResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new IdLabelResponse(toDoListService.getToDoListItemById(id)), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> addToDoListItem(@RequestBody ToDoListRequest toDoListRequest) {
        URI uri;
        try {
            ToDoList newToDoListItem = toDoListService.addToDoListItem(toDoListRequest);
            uri = new URI("/toDoList/" + newToDoListItem.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating toDoList: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }
}
