package com.timeOrganizer.service;

import com.timeOrganizer.exception.ActivityNotFoundException;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IActivityRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ActivityService implements IActivityService{
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
    public Activity createActivity(@NotNull ActivityRequest activityRequest) {
        Role role;
        if (activityRequest.getRoleId() != 0) {
            role = roleService.getRoleById(activityRequest.getRoleId());
        } else if (!Objects.equals(activityRequest.getNewRoleName(), "")) {
            role = roleService.createRole(activityRequest.getNewRoleName(),activityRequest.getNewRoleText());
        } else {
            throw new IllegalArgumentException("Role ID or new role name must be provided");
        }
        Category category;
        if (activityRequest.getCategoryId() != 0) {
           category = categoryService.getCategoryById(activityRequest.getCategoryId());
        } else if (!Objects.equals(activityRequest.getNewCategoryName(), "")) {
            category = categoryService.createCategory(activityRequest.getNewCategoryName(),activityRequest.getNewCategoryText());
        } else {
            throw new IllegalArgumentException("Category ID or new category name must be provided");
        }
        Activity activity = new Activity(activityRequest.getName(),activityRequest.getText(),activityRequest.getIsOnToDoList(),activityRequest.getIsNecessary(),role,category);
        return activityRepository.save(activity);
    }
    @Override
    public void deleteActivityById(@NotNull Long id) {
        activityRepository.deleteById(id);
    }
    @Override
    public Activity updateActivityById(Long id,@NotNull ActivityRequest activityRequest) {
        Activity activity = this.getActivityById(id);
        activity.setText(activityRequest.getName());
        return activityRepository.save(activity);
    }
    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Boolean isActivityPresent(Long id){
        return activityRepository.findById(id).isPresent();
    }
}
