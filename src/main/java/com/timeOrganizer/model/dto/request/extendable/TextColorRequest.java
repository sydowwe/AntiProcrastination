package com.timeOrganizer.model.dto.request.extendable;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class TextColorRequest implements IRequest {
    private String text;
    private String color;
}
