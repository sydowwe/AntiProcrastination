package com.timeOrganizer.model.dto.response.toDoList;

import com.timeOrganizer.model.dto.response.extendable.TextColorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UrgencyResponse extends TextColorResponse {
    private int priority;
//    private String icon;
}
