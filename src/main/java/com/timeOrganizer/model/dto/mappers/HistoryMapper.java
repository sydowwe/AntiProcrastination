package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.history.HistoryRequest;
import com.timeOrganizer.model.dto.response.history.HistoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class HistoryMapper extends AbstractInOutMapper<History, HistoryRequest, HistoryResponse>{
    private final ActivityMapper activityMapper;
    @Override
    public HistoryResponse convertToFullResponse(History history) {
        return HistoryResponse.builder()
                .id(history.getId())
                .activity(activityMapper.convertToFullResponse(history.getActivity()))
	        .startTimestamp(history.getStartTimestamp())
	        .endTimestamp(history.getEndTimestamp())
                .length(history.getLength())
                .build();
    }
    @Override
    public History updateEntityFromRequest(History entity, HistoryRequest request, Map<String, ? extends AbstractEntity> dependencies) {
	    entity.setStartTimestamp(request.getStartTimestamp());
        entity.setLength(request.getLength());
	    entity.setEndTimestamp(request.getStartTimestamp().plusSeconds(request.getLength().getInSeconds()));
	    entity.setActivity(this.getEntityFromDependencies(Activity.class, dependencies));
        return entity;
    }

    @Override
    protected History createEmptyEntity() {
        return new History();
    }
}