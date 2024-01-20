package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.response.toDoList.BaseToDoListResponse;
import com.timeOrganizer.model.dto.request.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ToDoListRequest extends BaseToDoListRequest {
    private long urgencyId;
}
