package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.ToDoListResponse;
import com.timeOrganizer.model.entity.ToDoList;
import org.springframework.stereotype.Component;

@Component
public class ToDoListMapper {
    public static ToDoListResponse convertToFullResponse(ToDoList toDoListItem) {
        return ToDoListResponse.builder()
                .id(toDoListItem.getId())
                .name(toDoListItem.getName())
                .text(toDoListItem.getText())
                .isDone(toDoListItem.isDone())
                .urgency(new UrgencyMapper().convertToFullResponse(toDoListItem.getUrgency()))
                .build();
    }
}
