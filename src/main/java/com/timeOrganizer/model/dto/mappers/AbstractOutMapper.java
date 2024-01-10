package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.IResponse;
import com.timeOrganizer.model.entity.AbstractEntity;

import java.util.List;

public abstract class AbstractOutMapper <ENTITY extends AbstractEntity, RESPONSE extends IResponse>{
    public List<RESPONSE> convertToFullResponseList(List<ENTITY> entityList) {
        return entityList.stream()
                .map(this::convertToFullResponse)
                .toList();
    }
    public abstract RESPONSE convertToFullResponse(ENTITY entity);
}
