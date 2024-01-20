package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.request.NameTextRequest;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuppressWarnings("unused")
public class BaseToDoListRequest extends NameTextRequest {
    private boolean isDone = false;
}
