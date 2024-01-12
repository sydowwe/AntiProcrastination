package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ActivityMapper extends AbstractInOutMapper<Activity,ActivityResponse, ActivityRequest>{
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
        entity.setName(request.getActivity());
        entity.setText(request.getDescription());
        entity.setOnToDoList(request.getIsOnToDoList());
        entity.setOnToDoList(request.getIsObligatory());
        entity.setRole((Role) dependencies.get("role"));
        entity.setCategory((Category) dependencies.get("category"));
        return entity;
    }

    @Override
    Activity createEmptyEntity() {
        return new Activity();
    }
}
