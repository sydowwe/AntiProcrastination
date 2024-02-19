package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.toDoList.UrgencyRequest;
import com.timeOrganizer.model.dto.response.toDoList.UrgencyResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Urgency;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UrgencyMapper  extends AbstractInOutMapper<Urgency, UrgencyRequest, UrgencyResponse> {
    @Override
    public UrgencyResponse convertToFullResponse(Urgency urgency) {
        return UrgencyResponse.builder()
                .id(urgency.getId())
                .text(urgency.getText())
                .color(urgency.getColor())
                .priority(urgency.getPriority()).build();
//        .icon(urgency.getIcon());
    }
    @Override
    public Urgency updateEntityFromRequest(Urgency entity, UrgencyRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setPriority(request.getPriority());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        return entity;
    }
    @Override
    protected Urgency createEmptyEntity() {
        return new Urgency();
    }
}
