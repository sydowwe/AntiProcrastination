package com.timeOrganizer.service;

import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Role;

import java.util.List;

public interface IRoleService {
    Role getRoleById(Long id);
    List<Role> getAllRoles();
    Role createRole(String name);
    public void deleteRole(Long id);
    Role updateRole(Long id,String newName);
    List<Activity> getActivitiesByRole(Long id);
}
