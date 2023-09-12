package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.ToDoListResponse;
import com.timeOrganizer.model.entity.ToDoList;
import org.springframework.stereotype.Component;

@Component
public class ToDoListMapper {
    public ToDoListResponse convertToFullResponse(ToDoList toDoListItem) {
        ToDoListResponse response = new ToDoListResponse();
        response.setId(toDoListItem.getId());
        response.setName(toDoListItem.getName());
        response.setText(toDoListItem.getText());
        response.setDone(toDoListItem.isDone());
        response.setUrgency(new UrgencyMapper().convertToFullResponse(toDoListItem.getUrgency()));
        return response;
    }
}
