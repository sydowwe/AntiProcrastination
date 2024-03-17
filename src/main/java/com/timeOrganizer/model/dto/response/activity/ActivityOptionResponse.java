package com.timeOrganizer.model.dto.response.activity;

import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class ActivityOptionResponse extends SelectOptionResponse
{
	private long roleId;
	private Long categoryId;
	public ActivityOptionResponse(ActivityResponse activityResponse)
	{
		super(activityResponse);
		this.roleId = activityResponse.getRole().getId();
		this.categoryId = activityResponse.getCategory() != null ? activityResponse.getCategory().getId() : null;
	}
}
