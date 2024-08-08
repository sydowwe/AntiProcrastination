package com.timeOrganizer.model.dto.mappers.toDoList;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import com.timeOrganizer.model.entity.RoutineToDoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RoutineToDoListMapper extends AbstractInOutMapper<RoutineToDoList, RoutineToDoListRequest, RoutineToDoListResponse> {
    private final TimePeriodMapper timePeriodMapper;
    private final ActivityMapper activityMapper;
    @Override
    public RoutineToDoListResponse convertToFullResponse(RoutineToDoList toDoListItem) {
        return RoutineToDoListResponse.builder()
                .id(toDoListItem.getId())
                .isDone(toDoListItem.isDone())
                .activity(activityMapper.convertToFullResponse(toDoListItem.getActivity()))
                .timePeriod(timePeriodMapper.convertToFullResponse(toDoListItem.getTimePeriod()))
                .build();
    }
    @Override
    public RoutineToDoList updateEntityFromRequest(RoutineToDoList entity, RoutineToDoListRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setDone(request.isDone());
        entity.setActivity(this.getEntityFromDependencies(Activity.class, dependencies));
        entity.setTimePeriod(this.getEntityFromDependencies(RoutineTimePeriod.class, dependencies));
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
