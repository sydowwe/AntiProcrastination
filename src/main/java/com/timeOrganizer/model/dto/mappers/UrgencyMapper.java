package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.UrgencyRequest;
import com.timeOrganizer.model.dto.response.UrgencyResponse;
import com.timeOrganizer.model.entity.Urgency;
import com.timeOrganizer.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UrgencyMapper extends AbstractInOutMapper<Urgency, UrgencyResponse, UrgencyRequest> {
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
    public Urgency createEntityFromRequest(UrgencyRequest request, User user) {
        return Urgency.builder()
                .priority(request.getPriority())
                .text(request.getText())
                .color(request.getColor())
                .user(user)
                .build();
    }
    @Override
    public Urgency updateEntityFromRequest(Urgency entity, UrgencyRequest request) {
        entity.setPriority(request.getPriority());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        return entity;
    }
}
