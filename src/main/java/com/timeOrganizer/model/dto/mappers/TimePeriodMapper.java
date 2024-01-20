package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.TimePeriodResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TimePeriodMapper extends AbstractInOutMapper<RoutineTimePeriod, TimePeriodRequest, TimePeriodResponse> {
    @Override
    public TimePeriodResponse convertToFullResponse(RoutineTimePeriod timePeriod) {
        return TimePeriodResponse.builder()
                .id(timePeriod.getId())
                .text(timePeriod.getText())
                .color(timePeriod.getColor())
                .length(timePeriod.getLength()).build();
    }
    @Override
    public RoutineTimePeriod updateEntityFromRequest(RoutineTimePeriod entity, TimePeriodRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setLength(request.getLength());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        return entity;
    }

    @Override
    protected RoutineTimePeriod createEmptyEntity() {
        return new RoutineTimePeriod();
    }
}
