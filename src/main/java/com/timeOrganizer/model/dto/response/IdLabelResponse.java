package com.timeOrganizer.model.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IdLabelResponse extends IdResponse {
    private String label;
    public IdLabelResponse(NameTextResponse nameTextResponse){
        super(nameTextResponse.getId());
        this.setLabel(nameTextResponse.getName());
    }
}
