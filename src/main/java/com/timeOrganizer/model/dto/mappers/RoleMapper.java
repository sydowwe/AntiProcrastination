package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Role;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    public Role updateEntityFromRequest(Role entity, NameTextColorIconRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        entity.setIcon(request.getIcon());
        return entity;
    }

    @Override
    Role createEmptyEntity() {
        return new Role();
    }
}