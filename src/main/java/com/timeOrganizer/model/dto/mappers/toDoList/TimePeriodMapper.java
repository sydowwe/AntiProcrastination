package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.toDoList.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.toDoList.TimePeriodResponse;
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
                .lengthInDays(timePeriod.getLengthInDays())
                .isHiddenInView(timePeriod.isHiddenInView()).build();
    }
    @Override
    public RoutineTimePeriod updateEntityFromRequest(RoutineTimePeriod entity, TimePeriodRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setLengthInDays(request.getLength());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        entity.setHiddenInView(request.isHiddenInView());
        return entity;
    }

    @Override
    protected RoutineTimePeriod createEmptyEntity() {
        return new RoutineTimePeriod();
    }
}
