package com.timeOrganizer.model.dto.response.toDoList;

import com.timeOrganizer.model.dto.response.UrgencyResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ToDoListResponse extends BaseToDoListResponse {
    private UrgencyResponse urgency;
}