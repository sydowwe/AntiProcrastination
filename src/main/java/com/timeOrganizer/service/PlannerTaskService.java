package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.PlannerTaskMapper;
import com.timeOrganizer.model.dto.request.PlannerTaskRequest;
import com.timeOrganizer.model.dto.response.PlannerTaskResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.PlannerTask;
import com.timeOrganizer.repository.IPlannerTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class PlannerTaskService extends MyService<PlannerTask, IPlannerTaskRepository, PlannerTaskRequest, PlannerTaskResponse, PlannerTaskMapper> {
	private final ActivityService activityService;
	@Autowired
	public PlannerTaskService(IPlannerTaskRepository repository, PlannerTaskMapper mapper, ActivityService activityService) {
		super(repository, mapper);
		this.activityService = activityService;
	}
	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(PlannerTaskRequest request) {
		return request.getActivityId() != null ? Map.of("activity",activityService.getReference(request.getActivityId())) : Map.of();
	}
	@Override
	protected Sort.Direction getSortDirection(){
		return Sort.Direction.ASC;
	}
	@Override
	protected String getSortByProperties(){
		return "startTimestamp";
	}
}