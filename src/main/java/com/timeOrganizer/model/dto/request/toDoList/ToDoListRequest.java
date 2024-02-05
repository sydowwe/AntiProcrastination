package com.timeOrganizer.model.dto.request.toDoList;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ToDoListRequest extends BaseToDoListRequest {
    private long urgencyId;
}
