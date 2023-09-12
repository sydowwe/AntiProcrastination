package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.ActivityResponse;
import com.timeOrganizer.model.entity.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
    public ActivityResponse convertToFullResponse(Activity activity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setName(activity.getName());
        response.setText(activity.getText());
        response.setOnToDoList(activity.isOnToDoList());
        response.setUnavoidable(activity.isUnavoidable());

        if (activity.getRole() != null) {
            response.setRole(new RoleMapper().convertToFullResponse(activity.getRole()));
        }

        if (activity.getCategory() != null) {
            response.setCategory(new CategoryMapper().convertToFullResponse(activity.getCategory()));
        }
        return response;
    }
}
