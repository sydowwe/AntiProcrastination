package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.mappers.ToDoListMapper;
import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.dto.response.*;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.service.ToDoListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/to-do-list")
@RequiredArgsConstructor
public class ToDoListController extends MyController {
    private final ToDoListService toDoListService;
    private final ToDoListMapper toDoListMapper;
    @PostMapping("/get-all")
    public ResponseEntity<List<ToDoListResponse>> getAllToDoListItems() {
        List<ToDoListResponse> toDoListResponseList = toDoListService.getAllToDoListItems().stream().map(ToDoListMapper::convertToFullResponse).toList();
        return new ResponseEntity<>(toDoListResponseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id) {
        return new ResponseEntity<>(new IdLabelResponse(toDoListService.getToDoListItemById(id)), HttpStatus.OK);
    }

    @PostMapping("/add")
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
    @PutMapping("/{id}")
    public ResponseEntity<IResponse> editToDoListItem(@PathVariable("id") Long id,@RequestBody ToDoListRequest toDoListRequest) {
        try {
            ToDoList toDoList = toDoListService.updateToDoListItem(id,toDoListRequest);
            return new ResponseEntity<>( ToDoListMapper.convertToFullResponse(toDoList), HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getReason(),ex.getMessage()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse("Unknown error editing toDoList",ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<IResponse> deleteToDoListItem(@PathVariable("id") Long id) {
        try {
            toDoListService.deleteToDoListItem(id);
            return new ResponseEntity<>( new IdResponse(id), HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ErrorResponse(ex.getReason(),ex.getMessage()), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>(new ErrorResponse("Unknown error deleting toDoList",ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
