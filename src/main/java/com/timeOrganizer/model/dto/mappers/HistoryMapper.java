package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.model.entity.History;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {
    public HistoryResponse convertToFullResponse(History history){
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setId(history.getId());
        historyResponse.setActivity(new ActivityMapper().convertToFullResponse(history.getActivity()));
        historyResponse.setStartTimestamp(history.getStart().toLocalDateTime().toString());
        historyResponse.setLength(history.getLengthInSeconds());
        return historyResponse;
    }
}