package com.timeOrganizer.model.dto.request.toDoList;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class RoutineToDoListRequest extends BaseToDoListRequest {
    private long timePeriodId;
}