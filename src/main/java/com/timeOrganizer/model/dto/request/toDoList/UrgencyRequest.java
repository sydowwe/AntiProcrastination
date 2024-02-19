package com.timeOrganizer.model.dto.request.toDoList;

import com.timeOrganizer.model.dto.request.extendable.TextColorRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class UrgencyRequest extends TextColorRequest {
    private int priority;
}
