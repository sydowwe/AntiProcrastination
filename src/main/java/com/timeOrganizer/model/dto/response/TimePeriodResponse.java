package com.timeOrganizer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TimePeriodResponse extends TextColorResponse{
    private int length;
}
