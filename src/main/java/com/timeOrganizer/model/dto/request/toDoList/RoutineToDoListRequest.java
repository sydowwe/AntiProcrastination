package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.request.NameTextRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class RoutineToDoListRequest extends NameTextRequest {
    private long timePeriodId;
}