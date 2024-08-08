package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import com.timeOrganizer.model.dto.response.extendable.IResponse;
import com.timeOrganizer.model.entity.AbstractEntity;

import java.util.Map;

public abstract class AbstractInOutMapper<ENTITY extends AbstractEntity, REQUEST extends IRequest, RESPONSE extends IResponse> extends AbstractOutMapper<ENTITY, RESPONSE>
{

	public ENTITY createEntityFromRequest(REQUEST request, Map<String, ? extends AbstractEntity> dependencies)
	{
		ENTITY entity = this.createEmptyEntity();
		entity = this.updateEntityFromRequest(entity, request, dependencies);
		return entity;
	}

	public abstract ENTITY updateEntityFromRequest(ENTITY entity, REQUEST request, Map<String, ? extends AbstractEntity> dependencies);

	protected abstract ENTITY createEmptyEntity();

	protected final <DEPENDENCY_CLASS extends AbstractEntity> DEPENDENCY_CLASS getEntityFromDependencies(Class<DEPENDENCY_CLASS> type, Map<String, ? extends AbstractEntity> dependencies)
	{
		DEPENDENCY_CLASS dependency = null;
		var obj = dependencies.get(type.getSimpleName());
		if (type.isInstance(obj)) {
			dependency = type.cast(obj);
		}
		return dependency;
	}
}
