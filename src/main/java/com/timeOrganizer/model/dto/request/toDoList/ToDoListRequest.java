package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.request.NameTextRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ToDoListRequest extends NameTextRequest {
    private long urgencyId;
}
