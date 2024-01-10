package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.response.ActivityResponse;

import java.util.List;

public interface IActivityService {
    List<ActivityResponse> getActivitiesByRoleId(long roleId, long userId);
     List<ActivityResponse> getActivitiesByCategoryId(long categoryId, long userId);
}
