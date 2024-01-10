package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.repository.IActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActivityService extends MyService<Activity,IActivityRepository,ActivityResponse,ActivityRequest,ActivityMapper> implements IActivityService {

    private final RoleService roleService;
    private final CategoryService categoryService;

    @Autowired
    public ActivityService(IActivityRepository repository, ActivityMapper mapper, UserService userService, RoleService roleService, CategoryService categoryService) {
        super(repository, mapper, userService);
        this.roleService = roleService;
        this.categoryService = categoryService;
    }

    @Override
    public List<ActivityResponse> getActivitiesByRoleId(long roleId, long userId) {
        return this.mapper.convertToFullResponseList(this.repository.findByRoleIdAndUserId(roleId, userId));
    }
    @Override
    public List<ActivityResponse> getActivitiesByCategoryId(long categoryId, long userId) {
        return this.mapper.convertToFullResponseList(this.repository.findByCategoryIdAndUserId(categoryId, userId));
    }
}
