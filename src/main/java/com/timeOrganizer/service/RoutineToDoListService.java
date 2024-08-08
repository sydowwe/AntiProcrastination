package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.toDoList.RoutineToDoListMapper;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import com.timeOrganizer.model.entity.RoutineToDoList;
import com.timeOrganizer.repository.IRoutineToDoListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoutineToDoListService extends MyService<RoutineToDoList, IRoutineToDoListRepository, RoutineToDoListRequest, RoutineToDoListResponse, RoutineToDoListMapper> implements IRoutineTodoListService
{
	private final RoutineToDoListTimePeriodTimePeriodService timePeriodService;
	private final ActivityService activityService;

	@Autowired
	public RoutineToDoListService(IRoutineToDoListRepository repository, RoutineToDoListMapper mapper, ActivityService activityService,RoutineToDoListTimePeriodTimePeriodService timePeriodService)
	{
		super(repository, mapper);
		this.activityService = activityService;
		this.timePeriodService = timePeriodService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(RoutineToDoListRequest request)
	{
		return this.getMapFromDependencies(activityService.getReference(request.getActivityId()), timePeriodService.getReference(request.getTimePeriodId()));
	}

	@Override
	public void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException
	{
		this.repository.updateIsDoneByIds(requestList.stream().map(IdRequest::getId).toList());
	}

	@Override
	public List<RoutineToDoListGroupedResponse> getAllByUserIdGroupedByTimePeriod()
	{
		var allPossibleTimePeriods = timePeriodService.getAll();
		Map<RoutineTimePeriod, List<RoutineToDoList>> groupedByTimePeriod = allPossibleTimePeriods.stream()
			.collect(Collectors.toMap(
				timePeriod -> timePeriod,
				timePeriod -> new ArrayList<>()
			));
		var allItems = this.repository.findAllByUserId(UserService.getLoggedUser().getId(), this.getSort());
		groupedByTimePeriod.putAll(allItems.stream().collect(Collectors.groupingBy(RoutineToDoList::getTimePeriod)));
		return this.mapper.convertToGroupedResponseAndSort(groupedByTimePeriod);
	}
}