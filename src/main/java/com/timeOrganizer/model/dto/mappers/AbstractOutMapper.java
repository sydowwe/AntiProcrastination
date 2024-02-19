package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.extendable.IResponse;
import com.timeOrganizer.model.entity.IEntity;

import java.util.List;

public abstract class AbstractOutMapper <ENTITY extends IEntity, RESPONSE extends IResponse>{
    public List<RESPONSE> convertToFullResponseList(List<ENTITY> entityList) {
        return entityList.stream()
                .map(this::convertToFullResponse)
                .toList();
    }
    public abstract RESPONSE convertToFullResponse(ENTITY entity);
}
