package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    public RoleResponse convertToFullResponse(Role role) {
        RoleResponse response = new RoleResponse();
        response.setId(role.getId());
        response.setName(role.getName());
        response.setText(role.getText());
        response.setColor(role.getColor());
        response.setIcon(role.getIcon());
        return response;
    }
}