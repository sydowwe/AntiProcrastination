package com.timeOrganizer.model.dto.request.extendable;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class NameTextRequest implements IRequest {
    private String name;
    private String text;
}
