package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ActivityMapper extends AbstractInOutMapper<Activity, ActivityRequest,ActivityResponse>{
    private final RoleMapper roleMapper;
    private final CategoryMapper categoryMapper;
    @Override
    public ActivityResponse convertToFullResponse(Activity activity) {
        ActivityResponse response = ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .text(activity.getText())
                .isOnToDoList(activity.isOnToDoList())
                .isUnavoidable(activity.isUnavoidable())
                .build();
        if (activity.getRole() != null) {
            response.setRole(roleMapper.convertToFullResponse(activity.getRole()));
        }
        if (activity.getCategory() != null) {
            response.setCategory(categoryMapper.convertToFullResponse(activity.getCategory()));
        }
        return response;
    }
    @Override
    public Activity updateEntityFromRequest(Activity entity, ActivityRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setOnToDoList(request.isOnToDoList());
        entity.setUnavoidable(request.isUnavoidable());
        entity.setRole(this.getEntityFromDependencies(Role.class, dependencies));
        entity.setCategory(this.getEntityFromDependencies(Category.class, dependencies));
        return entity;
    }

    @Override
    protected Activity createEmptyEntity() {
        return new Activity();
    }
}
