package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.service.CategoryService;
import com.timeOrganizer.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityMapper extends AbstractInOutMapper<Activity,ActivityResponse, ActivityRequest>{
    private final RoleMapper roleMapper;
    private final CategoryMapper categoryMapper;
    private final RoleService roleService;
    private final CategoryService categoryService;
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
    public Activity createEntityFromRequest(ActivityRequest request, User user) {
        return Activity.builder()
                .name(request.getActivity())
                .text(request.getDescription())
                .isOnToDoList(request.getIsOnToDoList())
                .isUnavoidable(request.getIsObligatory())
                .user(user)
                .role(this.roleService.getReference(request.getRoleId()))
                .category(this.categoryService.getReference(request.getCategoryId()))
                .build();
    }
    @Override
    public Activity updateEntityFromRequest(Activity entity, ActivityRequest request) {
        entity.setName(request.getActivity());
        entity.setText(request.getDescription());
        entity.setOnToDoList(request.getIsOnToDoList());
        entity.setOnToDoList(request.getIsObligatory());
        entity.setRole(this.roleService.getReference(request.getRoleId()));
        entity.setCategory(this.categoryService.getReference(request.getCategoryId()));
        return entity;
    }
}
