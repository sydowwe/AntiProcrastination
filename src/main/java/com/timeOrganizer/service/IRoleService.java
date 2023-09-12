package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.entity.Role;

import java.util.List;

public interface IRoleService {
    Role getRoleById(Long id);
    List<Role> getAllRoles();
    Role createRole(NameTextColorIconRequest newRoleRequest);
    void deleteRole(Long id);
    Role updateRole(Long id, NameTextColorIconRequest roleRequest);
}
