package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ToDoListResponse extends NameTextResponse {
    private UrgencyResponse urgency;
    private boolean isDone;
}
