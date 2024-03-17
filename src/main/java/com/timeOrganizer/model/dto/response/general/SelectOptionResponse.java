package com.timeOrganizer.model.dto.response.general;

import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.extendable.NameTextResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SelectOptionResponse extends IdResponse {
    private String label;
    public SelectOptionResponse(NameTextResponse nameTextResponse){
        super(nameTextResponse.getId());
        this.setLabel(nameTextResponse.getName());
    }
    public SelectOptionResponse(long id, String name){
        super(id);
        this.setLabel(name);
    }
}
