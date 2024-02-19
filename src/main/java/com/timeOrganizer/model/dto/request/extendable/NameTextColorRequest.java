package com.timeOrganizer.model.dto.request.extendable;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class NameTextColorRequest extends NameTextRequest {
    private String color;
}
