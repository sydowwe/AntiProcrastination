package com.timeOrganizer.model.dto.request.extendable;

import lombok.Getter;

@Getter
@SuppressWarnings("unused")
public class ActivityIdRequest implements IRequest
{
	private long activityId;
}
