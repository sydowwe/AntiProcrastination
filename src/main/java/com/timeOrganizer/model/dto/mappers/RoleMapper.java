package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractInOutMapper<Role, RoleResponse, NameTextColorIconRequest>{
    @Override
    public RoleResponse convertToFullResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .text(role.getText())
                .color(role.getColor())
                .icon(role.getIcon()).build();
    }
    @Override
    public Role createEntityFromRequest(NameTextColorIconRequest request, User user) {
        return Role.builder()
                .name(request.getName())
                .text(request.getText())
                .color(request.getColor())
                .icon(request.getIcon())
                .user(user)
                .build();
    }
    @Override
    public Role updateEntityFromRequest(Role entity, NameTextColorIconRequest request) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        entity.setIcon(request.getIcon());
        return entity;
    }
}