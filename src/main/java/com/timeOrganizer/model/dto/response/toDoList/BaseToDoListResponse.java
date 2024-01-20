package com.timeOrganizer.model.dto.response.toDoList;

import com.timeOrganizer.model.dto.response.IResponse;
import com.timeOrganizer.model.dto.response.NameTextResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class BaseToDoListResponse extends NameTextResponse {
    private boolean isDone = false;
}
