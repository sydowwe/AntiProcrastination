package com.timeOrganizer.service;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.entity.Activity;

import java.util.List;

public interface IActivityService {
    Activity createActivity(ActivityRequest activityRequest);
    void deleteActivityById(Long id);
    Activity updateActivityById(Long id,ActivityRequest request);
    Activity getActivityById(Long id);
    List<Activity> getAllActivities();
    Boolean isActivityPresent(Long id);
}
