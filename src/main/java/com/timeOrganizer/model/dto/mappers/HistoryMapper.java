package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.HistoryResponse;
import com.timeOrganizer.model.entity.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class HistoryMapper {

    private final ActivityMapper activityMapper;
    public HistoryResponse convertToFullResponse(History history) {
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setId(history.getId());
        historyResponse.setActivity(activityMapper.convertToFullResponse(history.getActivity()));
        historyResponse.setStartTimestamp(history.getStart().toLocalDateTime().toString());
        historyResponse.setLength(history.getLengthInSeconds());
        return historyResponse;
    }

    public List<HistoryResponse> convertToFullResponseList(List<History> historyList) {
        return historyList.stream()
                .map(this::convertToFullResponse)
                .toList();
    }
}