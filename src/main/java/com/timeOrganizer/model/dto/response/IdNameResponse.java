package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdNameResponse {
    private Long id;
    private String name;
    public IdNameResponse(AbstractEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
