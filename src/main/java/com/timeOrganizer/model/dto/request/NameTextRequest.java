package com.timeOrganizer.model.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuppressWarnings("unused")
public class NameTextRequest implements IRequest {
    private String name;
    private String text;
}
