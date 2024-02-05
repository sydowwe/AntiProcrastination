package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.mappers.TimePeriodMapper;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoutineToDoListMapper extends AbstractInOutMapper<RoutineToDoList, RoutineToDoListRequest, RoutineToDoListResponse> {
    private final TimePeriodMapper timePeriodMapper;
    @Override
    public RoutineToDoListResponse convertToFullResponse(RoutineToDoList toDoListItem) {
        return RoutineToDoListResponse.builder()
                .id(toDoListItem.getId())
                .name(toDoListItem.getName())
                .text(toDoListItem.getText())
                .isDone(toDoListItem.isDone())
                .timePeriod(toDoListItem.getTimePeriod() != null ? timePeriodMapper.convertToFullResponse(toDoListItem.getTimePeriod()) : null)
                .build();
    }
    @Override
    public RoutineToDoList updateEntityFromRequest(RoutineToDoList entity, RoutineToDoListRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setDone(request.isDone());
        entity.setTimePeriod((RoutineTimePeriod) dependencies.get("timePeriod"));
        return entity;
    }
    @Override
    protected RoutineToDoList createEmptyEntity(){
        return new RoutineToDoList();
    }

    public List<RoutineToDoListGroupedResponse> convertToGroupedResponseAndSort(Map<RoutineTimePeriod, List<RoutineToDoList>> map){
        var groupedResponses = new java.util.ArrayList<>(map.entrySet().stream().map(item -> new RoutineToDoListGroupedResponse(timePeriodMapper.convertToFullResponse(item.getKey()), this.convertToFullResponseList(item.getValue()))).toList());
        groupedResponses.sort(Comparator.comparingInt(response -> response.getTimePeriod().getLengthInDays()));
        return groupedResponses;
    }
}
