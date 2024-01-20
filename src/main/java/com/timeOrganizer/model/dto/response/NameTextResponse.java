package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class NameTextResponse extends IdResponse{
    private String name;
    private String text;
}
