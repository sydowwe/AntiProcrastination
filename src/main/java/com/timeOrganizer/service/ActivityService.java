package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.repository.IActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivityService extends MyService<Activity,IActivityRepository,ActivityRequest,ActivityResponse,ActivityMapper> implements IActivityService {
    private final RoleService roleService;
    private final CategoryService categoryService;
    @Autowired
    public ActivityService(IActivityRepository repository, ActivityMapper mapper, RoleService roleService, CategoryService categoryService) {
        super(repository, mapper);
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

    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(ActivityRequest request) {
        return Map.of("role",this.roleService.getReference(request.getCategoryId()),"category",this.categoryService.getReference(request.getCategoryId()));
    }
}
