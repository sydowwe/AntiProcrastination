package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdLabelResponse {
    private Long id;
    private String label;
    public IdLabelResponse(AbstractEntity entity) {
        this.id = entity.getId();
        this.label = entity.getName();
    }
}
