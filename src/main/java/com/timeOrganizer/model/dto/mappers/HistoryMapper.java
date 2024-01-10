package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;


@Component
@RequiredArgsConstructor
public class HistoryMapper extends AbstractInOutMapper<History, HistoryResponse, HistoryRequest>{
    private final ActivityService activityService;
    private final ActivityMapper activityMapper;
    @Override
    public HistoryResponse convertToFullResponse(History history) {
        return HistoryResponse.builder()
                .id(history.getId())
                .activity(activityMapper.convertToFullResponse(history.getActivity()))
                .startTimestamp(history.getStart().toLocalDateTime().toString())
                .length(history.getLengthInSeconds())
                .build();
    }
    @Override
    public History createEntityFromRequest(HistoryRequest request, User user) {
        return History.builder()
                .activity(activityService.getReference(request.getActivityId()))
                .length(request.getLength())
                .start(ZonedDateTime.parse(request.getStartTimestamp()))
                .build();
    }
    @Override
    public History updateEntityFromRequest(History entity, HistoryRequest request) {
        entity.setStart(ZonedDateTime.parse(request.getStartTimestamp()));
        entity.setLength(request.getLength());
        entity.setActivity(activityService.getReference(request.getActivityId()));
        return entity;
    }
}