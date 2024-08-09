package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.toDoList.ToDoListMapper;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.repository.IToDoListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ToDoListService extends EntityWithActivityService<ToDoList, IToDoListRepository, ToDoListRequest, ToDoListResponse, ToDoListMapper> implements IToDoListService
{
	private final UrgencyService urgencyService;

	@Autowired
	public ToDoListService(IToDoListRepository repository, ToDoListMapper mapper, ActivityService activityService,UrgencyService urgencyService)
	{
		super(repository, mapper, activityService);
		this.urgencyService = urgencyService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(ToDoListRequest request)
	{
		return this.getMapFromDependencies(activityService.getReference(request.getActivityId()), urgencyService.getReference(request.getUrgencyId()));
	}

	@Override
	protected Sort.Direction getSortDirection()
	{
		return Sort.Direction.ASC;
	}

	@Override
	protected String getSortByProperties()
	{
		return "urgency.priority";
	}

	@Override
	public void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException
	{
		int affectedRows = this.repository.updateIsDoneByIds(requestList.stream().map(IdRequest::getId).toList());
		if (affectedRows<=0){
			//throw new UpdateFailedException();
		}
	}
}
