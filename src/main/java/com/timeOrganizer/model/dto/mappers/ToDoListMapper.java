package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.dto.response.ToDoListResponse;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.service.UrgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ToDoListMapper extends AbstractInOutMapper<ToDoList,ToDoListResponse,ToDoListRequest>{
    private final UrgencyService urgencyService;
    @Override
    public ToDoListResponse convertToFullResponse(ToDoList toDoListItem) {
        return ToDoListResponse.builder()
                .id(toDoListItem.getId())
                .name(toDoListItem.getName())
                .text(toDoListItem.getText())
                .isDone(toDoListItem.isDone())
                .urgency(new UrgencyMapper().convertToFullResponse(toDoListItem.getUrgency()))
                .build();
    }
    @Override
    public ToDoList createEntityFromRequest(ToDoListRequest request, User user) {
        return ToDoList.builder()
                .name(request.getName())
                .text(request.getText())
                .urgency(urgencyService.getReference(request.getUrgencyId()))
                .isDone(false)
                .user(user)
                .build();
    }
    @Override
    public ToDoList updateEntityFromRequest(ToDoList entity, ToDoListRequest request) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setUrgency(urgencyService.getReference(request.getUrgencyId()));
        return null;
    }
}
