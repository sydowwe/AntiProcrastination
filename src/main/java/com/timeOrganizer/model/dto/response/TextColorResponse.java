package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TextColorResponse extends IdResponse{
    private String text;
    private String color;
}
