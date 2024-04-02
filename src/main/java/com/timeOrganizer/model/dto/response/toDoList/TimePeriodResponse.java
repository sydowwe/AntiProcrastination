package com.timeOrganizer.model.dto.response.toDoList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.response.extendable.TextColorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class TimePeriodResponse extends TextColorResponse {
    private int lengthInDays;
    @JsonProperty(value = "isHiddenInView")
    private boolean isHiddenInView;
}
