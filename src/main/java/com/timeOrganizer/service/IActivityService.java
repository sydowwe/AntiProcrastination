package com.timeOrganizer.service;
import com.timeOrganizer.model.dto.request.NewActivityRequest;
import com.timeOrganizer.model.entity.Activity;

import java.util.List;

public interface IActivityService {
    Activity createActivity(NewActivityRequest newActivityRequest);
    void deleteActivityById(Long id);
    Activity updateActivityById(Long id, NewActivityRequest request);
    Activity getActivityById(Long id);
    List<Activity> getAllActivities();
    Boolean isActivityPresent(Long id);
    public List<Activity> getActivitiesByRoleId(Long roleId);
    public List<Activity> getActivitiesByCategoryId(Long categoryId);
}
