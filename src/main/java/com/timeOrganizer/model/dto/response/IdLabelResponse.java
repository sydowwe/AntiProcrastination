package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.entity.AbstractEntity;
import lombok.Data;

@Data
public class IdLabelResponse implements IResponse{
    private Long id;
    private String label;
    public IdLabelResponse(AbstractEntity entity) {
        this.id = entity.getId();
        this.label = entity.getName();
    }
}
