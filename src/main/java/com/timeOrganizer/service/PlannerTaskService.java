package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.PlannerTaskMapper;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.request.taskPlanner.PlannerFilterRequest;
import com.timeOrganizer.model.dto.request.taskPlanner.PlannerTaskRequest;
import com.timeOrganizer.model.dto.response.PlannerTaskResponse;
import com.timeOrganizer.model.entity.PlannerTask;
import com.timeOrganizer.repository.IPlannerTaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PlannerTaskService extends EntityWithActivityService<PlannerTask, IPlannerTaskRepository, PlannerTaskRequest, PlannerTaskResponse, PlannerTaskMapper>
{
	@Autowired
	public PlannerTaskService(IPlannerTaskRepository repository, PlannerTaskMapper mapper, ActivityService activityService)
	{
		super(repository, mapper, activityService);
	}

	@Override
	protected Sort.Direction getSortDirection()
	{
		return Sort.Direction.ASC;
	}

	@Override
	protected String getSortByProperties()
	{
		return "start_timestamp";
	}

	public List<PlannerTaskResponse> getAllByDateAndHourSpan(PlannerFilterRequest request)
	{
		return mapper.convertToFullResponseList(
			repository.getAllByDateAndHourSpan(
				UserService.getLoggedUser().getId(),
				request.getFilterDate(),
				request.getFilterDate().plusSeconds(request.getHourSpan() * 3600L)
			));
	}

	public void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException
	{
		int affectedRows = this.repository.updateIsDoneByIds(requestList.stream().map(IdRequest::getId).toList());
		if (affectedRows <= 0) {
			//throw new UpdateFailedException();
		}
	}
}