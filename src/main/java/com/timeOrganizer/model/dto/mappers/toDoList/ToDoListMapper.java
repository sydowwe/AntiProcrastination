package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.TaskUrgency;
import com.timeOrganizer.model.entity.ToDoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ToDoListMapper extends AbstractInOutMapper<ToDoList,ToDoListRequest,ToDoListResponse> {
    private final UrgencyMapper urgencyMapper;
    private final ActivityMapper activityMapper;
    @Override
    public ToDoListResponse convertToFullResponse(ToDoList toDoListItem) {
        return ToDoListResponse.builder()
                .id(toDoListItem.getId())
                .isDone(toDoListItem.isDone())
                .activity(activityMapper.convertToFullResponse(toDoListItem.getActivity()))
            .urgency(urgencyMapper.convertToFullResponse(toDoListItem.getTaskUrgency()))
                .build();
    }
    @Override
    public ToDoList updateEntityFromRequest(ToDoList entity, ToDoListRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setActivity(this.getEntityFromDependencies(Activity.class, dependencies));
        entity.setTaskUrgency(this.getEntityFromDependencies(TaskUrgency.class, dependencies));
        return entity;
    }
    @Override
    protected ToDoList createEmptyEntity() {
        return new ToDoList();
    }
}
