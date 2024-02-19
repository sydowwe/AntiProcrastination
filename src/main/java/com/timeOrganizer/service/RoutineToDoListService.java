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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoutineToDoListService extends MyService<RoutineToDoList, IRoutineToDoListRepository, RoutineToDoListRequest, RoutineToDoListResponse, RoutineToDoListMapper> implements IRoutineTodoListService
{
	private final RoutineToDoListTimePeriodTimePeriodService timePeriodService;

	@Autowired
	public RoutineToDoListService(IRoutineToDoListRepository repository, RoutineToDoListMapper mapper, RoutineToDoListTimePeriodTimePeriodService timePeriodService)
	{
		super(repository, mapper);
		this.timePeriodService = timePeriodService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(RoutineToDoListRequest request)
	{
		return Map.of("timePeriod", timePeriodService.getReference(request.getTimePeriodId()));
	}

	@Override
	public void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException
	{
		this.repository.updateIsDoneByIds(requestList.stream().map(IdRequest::getId).toList());
	}

	@Override
	public List<RoutineToDoListGroupedResponse> getAllByUserIdGroupedByTimePeriod(long userId)
	{
		var allItems = this.repository.findAllByUserId(userId, this.getSort());
		Map<RoutineTimePeriod, List<RoutineToDoList>> groupedByTimePeriod = allItems.stream()
			.collect(Collectors.groupingBy(RoutineToDoList::getTimePeriod));
		return this.mapper.convertToGroupedResponseAndSort(groupedByTimePeriod);
	}
}