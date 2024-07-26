package com.timeOrganizer.model.dto.response.history;

import com.timeOrganizer.model.dto.response.extendable.IResponse;

import java.time.LocalDate;
import java.util.List;

public record HistoryListGroupedByDateResponse(LocalDate date, List<HistoryResponse> historyResponseList) implements IResponse
{
}
