package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class UrgencyResponse extends IdResponse {
    private String text;
    private String color;
    private int priority;
//    private String icon;
}
