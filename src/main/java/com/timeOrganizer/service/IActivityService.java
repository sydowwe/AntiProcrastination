package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.response.activity.ActivityResponse;

import java.util.List;

public interface IActivityService {
	List<ActivityResponse> getActivitiesByRoleId(long roleId);

	List<ActivityResponse> getActivitiesByCategoryId(long categoryId);
}
