package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class TimePeriodRequest extends TextColorRequest{
    private int length;
}
