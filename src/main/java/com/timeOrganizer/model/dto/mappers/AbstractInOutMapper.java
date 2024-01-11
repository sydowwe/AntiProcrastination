package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.IRequest;
import com.timeOrganizer.model.dto.response.IResponse;
import com.timeOrganizer.model.entity.IEntity;
import com.timeOrganizer.model.entity.User;

public abstract class AbstractInOutMapper<ENTITY extends IEntity, RESPONSE extends IResponse, REQUEST extends IRequest> extends AbstractOutMapper<ENTITY, RESPONSE>{
    public abstract ENTITY createEntityFromRequest(REQUEST request, User user);
    public abstract ENTITY updateEntityFromRequest(ENTITY entity, REQUEST request);
}
