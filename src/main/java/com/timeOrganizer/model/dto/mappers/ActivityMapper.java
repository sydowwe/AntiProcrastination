package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public ActivityResponse convertToFullResponse(Activity activity) {
        ActivityResponse response = ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .text(activity.getText())
                .isOnToDoList(activity.isOnToDoList())
                .isUnavoidable(activity.isUnavoidable())
                .build();

        if (activity.getRole() != null) {
            response.setRole(new RoleMapper().convertToFullResponse(activity.getRole()));
        }

        if (activity.getCategory() != null) {
            response.setCategory(new CategoryMapper().convertToFullResponse(activity.getCategory()));
        }
        return response;
    }
}
