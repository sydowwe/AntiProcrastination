package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.IRequest;
import com.timeOrganizer.model.dto.response.IResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.User;

import java.util.Map;

public abstract class AbstractInOutMapper<ENTITY extends AbstractEntity, REQUEST extends IRequest, RESPONSE extends IResponse> extends AbstractOutMapper<ENTITY, RESPONSE>{

    public ENTITY createEntityFromRequest(REQUEST request, User user, Map<String,? extends AbstractEntity> dependencies){
        ENTITY entity = this.createBaseEntityWithUser(user);
        entity = this.updateEntityFromRequest(entity,request,dependencies);
        return entity;
    }

    public abstract ENTITY updateEntityFromRequest(ENTITY entity, REQUEST request, Map<String, ? extends AbstractEntity> dependencies);
    protected abstract ENTITY createEmptyEntity();
    ENTITY createBaseEntityWithUser(User user){
        ENTITY entity = this.createEmptyEntity();
        entity.setUser(user);
        return entity;
    }
}
