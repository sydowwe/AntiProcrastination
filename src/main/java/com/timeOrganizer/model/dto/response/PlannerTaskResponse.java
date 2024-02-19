package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.dto.response.extendable.NameTextColorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PlannerTaskResponse extends NameTextColorResponse {
    private String startTimestamp;
    private int minuteLength;
    private ActivityResponse activity;
}
