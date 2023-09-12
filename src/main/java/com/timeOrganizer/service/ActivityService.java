package com.timeOrganizer.service;

import com.timeOrganizer.exception.ActivityNotFoundException;
import com.timeOrganizer.exception.NoActivityFoundWithParameterException;
import com.timeOrganizer.exception.RoleNotFoundException;
import com.timeOrganizer.model.dto.request.NewActivityRequest;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IActivityRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ActivityService implements IActivityService {
    private final RoleService roleService;
    private final CategoryService categoryService;
    private final IActivityRepository activityRepository;

    @Autowired
    public ActivityService(RoleService roleService, CategoryService categoryService, IActivityRepository activityRepository) {
        this.roleService = roleService;
        this.categoryService = categoryService;
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity getActivityById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException(id));
    }

    @Override
    public Activity createActivity(@NotNull NewActivityRequest newActivityRequest) {
        Role role;
        if (newActivityRequest.getRoleId() != null) {
            role = roleService.getRoleById(newActivityRequest.getRoleId());
        } else {
            throw new IllegalArgumentException("Role ID must be provided");
        }
        Category category;
        if (newActivityRequest.getCategoryId() != null) {
            category = categoryService.getCategoryById(newActivityRequest.getCategoryId());
        } else {
            throw new IllegalArgumentException("Category ID name must be provided");
        }
        Activity activity = new Activity(newActivityRequest.getActivity(), newActivityRequest.getDescription(), newActivityRequest.getIsOnToDoList(), newActivityRequest.getIsObligatory(), role, category);
        return activityRepository.save(activity);
    }

    @Override
    public void deleteActivityById(@NotNull Long id) {
        activityRepository.deleteById(id);
    }

    @Override
    public Activity updateActivityById(Long id, @NotNull NewActivityRequest newActivityRequest) {
        Activity activity = this.getActivityById(id);
        activity.setText(newActivityRequest.getActivity());
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Boolean isActivityPresent(Long id) {
        return activityRepository.findById(id).isPresent();
    }
    @Override
    public List<Activity> getActivitiesByRoleId(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Role role = roleService.getRoleById(roleId);
        if (role == null) {
            throw new RoleNotFoundException(roleId);
        }
        List<Activity> activities = activityRepository.findByRole(role);
        if (activities.isEmpty()){
            throw new NoActivityFoundWithParameterException("categoryId",roleId.toString());
        }
        return activities;
    }
    @Override
    public List<Activity> getActivitiesByCategoryId(Long categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Category category = categoryService.getCategoryById(categoryId);

        List<Activity> activities = activityRepository.findByCategory(category);
        if (activities.isEmpty()){
            throw new NoActivityFoundWithParameterException("categoryId",categoryId.toString());
        }
        return activities;
    }
}
