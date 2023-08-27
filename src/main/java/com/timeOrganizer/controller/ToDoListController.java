/*
package com.timeOrganizer.controller;

import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.dto.response.IdNameResponse;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/toDoList")
public class ToDoListController extends MyController{
    private final ToDoListService toDoListService;
    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<IdNameResponse>> getAllActivities(){
        System.out.println("adsdsad");
        return new ResponseEntity<>(mapToIdNameResponse(toDoListService.getAllActivities()), HttpStatus.OK);
    }
    @PostMapping("/get-by-toDoList")
    public ResponseEntity<Map<String,Long>> getToDoListById(@RequestBody Long id) {
        ToDoList toDoList = toDoListService.getToDoListById(id);
        Map<String, Long> responseData = new HashMap<>();
        responseData.put("toDoListId", toDoList.getId());
        responseData.put("roleId", toDoList.getRole().getId());
        responseData.put("categoryId", toDoList.getCategory().getId());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IdNameResponse> get(@PathVariable("id") Long id){
        return new ResponseEntity<>(new IdNameResponse(toDoListService.getToDoListById(id)), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createNewToDoList(@RequestBody ToDoListRequest toDoListRequest){
        URI uri;
        try {
            ToDoList newToDoList = toDoListService.createToDoList(toDoListRequest);
            uri = new URI("/toDoList/" + newToDoList.getId());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating toDoList: " + e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }*/
