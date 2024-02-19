package com.timeOrganizer.model.dto.response.general;

import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.extendable.NameTextResponse;
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
