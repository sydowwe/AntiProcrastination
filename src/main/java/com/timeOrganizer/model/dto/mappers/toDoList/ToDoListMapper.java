package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.mappers.UrgencyMapper;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.model.entity.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ToDoListMapper extends AbstractInOutMapper<ToDoList,ToDoListRequest,ToDoListResponse> {
    private final UrgencyMapper urgencyMapper;
    @Override
    public ToDoListResponse convertToFullResponse(ToDoList toDoListItem) {
        return ToDoListResponse.builder()
                .id(toDoListItem.getId())
                .name(toDoListItem.getName())
                .text(toDoListItem.getText())
                .isDone(toDoListItem.isDone())
                .urgency(toDoListItem.getUrgency() != null ? urgencyMapper.convertToFullResponse(toDoListItem.getUrgency()) : null)
                .build();
    }
    @Override
    public ToDoList updateEntityFromRequest(ToDoList entity, ToDoListRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setUrgency((Urgency) dependencies.get("urgency"));
        return entity;
    }
    @Override
    protected ToDoList createEmptyEntity() {
        return new ToDoList();
    }
}
