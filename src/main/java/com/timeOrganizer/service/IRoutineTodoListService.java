package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;

import java.util.List;

public interface IRoutineTodoListService extends IToDoListService
{
	List<RoutineToDoListGroupedResponse> getAllByUserIdGroupedByTimePeriod(long userId);
}
