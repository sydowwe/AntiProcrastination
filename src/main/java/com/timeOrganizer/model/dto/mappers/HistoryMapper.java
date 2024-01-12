package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.HistoryRequest;
import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.ZonedDateTime;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class HistoryMapper extends AbstractInOutMapper<History, HistoryResponse, HistoryRequest>{
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
    public History updateEntityFromRequest(History entity, HistoryRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setStart(ZonedDateTime.parse(request.getStartTimestamp()));
        entity.setLength(request.getLength());
        entity.setActivity((Activity) dependencies.get("activity"));
        return entity;
    }

    @Override
    History createEmptyEntity() {
        return new History();
    }
}