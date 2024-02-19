package com.timeOrganizer.model.dto.response.toDoList;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class RoutineToDoListResponse extends BaseToDoListResponse {
    private TimePeriodResponse timePeriod;
}
