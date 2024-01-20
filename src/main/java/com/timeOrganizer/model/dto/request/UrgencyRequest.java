package com.timeOrganizer.model.dto.request;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class UrgencyRequest extends TextColorRequest{
    private int priority;
}
