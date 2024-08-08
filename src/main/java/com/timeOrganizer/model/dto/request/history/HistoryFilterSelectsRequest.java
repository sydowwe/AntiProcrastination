package com.timeOrganizer.model.dto.request.history;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class HistoryFilterSelectsRequest implements IRequest
{
	private Long activityId;
	private Long roleId;
	private Long categoryId;
	private Boolean isFromToDoList;
	private Boolean isUnavoidable;
}
