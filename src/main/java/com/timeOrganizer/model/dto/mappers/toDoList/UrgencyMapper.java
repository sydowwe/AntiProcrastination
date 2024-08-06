package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.toDoList.UrgencyRequest;
import com.timeOrganizer.model.dto.response.toDoList.UrgencyResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.TaskUrgency;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrgencyMapper extends AbstractInOutMapper<TaskUrgency, UrgencyRequest, UrgencyResponse>
{
    @Override
    public UrgencyResponse convertToFullResponse(TaskUrgency taskUrgency)
    {
        return UrgencyResponse.builder()
	        .id(taskUrgency.getId())
	        .text(taskUrgency.getText())
	        .color(taskUrgency.getColor())
	        .priority(taskUrgency.getPriority()).build();
//        .icon(urgency.getIcon());
    }
    @Override
    public TaskUrgency updateEntityFromRequest(TaskUrgency entity, UrgencyRequest request, Map<String, ? extends AbstractEntity> dependencies)
    {
        entity.setPriority(request.getPriority());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        return entity;
    }
    @Override
    protected TaskUrgency createEmptyEntity()
    {
	    return new TaskUrgency();
    }
}
