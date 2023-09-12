package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.UrgencyResponse;
import com.timeOrganizer.model.entity.Urgency;
import org.springframework.stereotype.Component;

@Component
public class UrgencyMapper {
    public UrgencyResponse convertToFullResponse(Urgency urgency) {
        UrgencyResponse response = new UrgencyResponse();
        response.setId(urgency.getId());
        response.setText(urgency.getText());
        response.setColor(urgency.getColor());
        response.setIcon(urgency.getIcon());
        return response;
    }
}
