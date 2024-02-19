package com.timeOrganizer.model.dto.response.toDoList;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@AllArgsConstructor
@Data
public class RoutineToDoListGroupedResponse {
    private TimePeriodResponse timePeriod;
    private List<RoutineToDoListResponse> items;
}
