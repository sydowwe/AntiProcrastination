package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.entity.ToDoList;

import java.util.List;

public interface IToDoListService {
    ToDoList getToDoListItemById(Long id);
    List<ToDoList> getAllToDoListItems();
    ToDoList addToDoListItem(ToDoListRequest toDoListRequest);
    void deleteToDoListItem(Long id);
    ToDoList updateToDoListItem(Long id,ToDoListRequest toDoListRequest);
/*    List<Activity> getActivitiesByToDoListUrgency(Long id);*/
}
