package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.AbstractInOutMapper;
import com.timeOrganizer.model.dto.request.extendable.ActivityIdRequest;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.EntityWithActivity;
import com.timeOrganizer.repository.IEntityWithActivityRepository;

import java.util.Map;

public abstract class EntityWithActivityService<ENTITY extends EntityWithActivity, REPOSITORY extends IEntityWithActivityRepository<ENTITY>, REQUEST extends ActivityIdRequest, RESPONSE extends IdResponse, MAPPER extends AbstractInOutMapper<ENTITY, REQUEST, RESPONSE>>
	extends MyService<ENTITY, REPOSITORY, REQUEST, RESPONSE, MAPPER>
{
	protected final ActivityService activityService;

	public EntityWithActivityService(REPOSITORY repository, MAPPER mapper, ActivityService activityService)
	{
		super(repository, mapper);
		this.activityService = activityService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(REQUEST request)
	{
		return this.getMapFromDependencies(activityService.getReference(request.getActivityId()));
	}
//	public ActivityFormSelectsResponse updateFilterSelects(ActivitySelectForm request)
//	{
//		Specification<EntityWithActivity> spec = AbstractActivitySpecifications.withFilter(
//			UserService.getLoggedUser().getId(),
//			request.getActivityId(),
//			request.getRoleId(),
//			request.getCategoryId()
//		);
//		var list = this.repository.findAll(spec);
//		return activityService.getActivityFormSelectsFromActivityList(list.);
//	}
}
