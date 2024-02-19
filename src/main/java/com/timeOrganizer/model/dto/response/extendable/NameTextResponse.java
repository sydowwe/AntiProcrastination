package com.timeOrganizer.model.dto.response.extendable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class NameTextResponse extends IdResponse{
    private String name;
    private String text;
}
