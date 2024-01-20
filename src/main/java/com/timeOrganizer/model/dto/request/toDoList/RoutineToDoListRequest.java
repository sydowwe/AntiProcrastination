package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.response.toDoList.BaseToDoListResponse;
import com.timeOrganizer.model.dto.request.IRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuppressWarnings("unused")
public class RoutineToDoListRequest extends BaseToDoListRequest {
    private long timePeriodId;
}