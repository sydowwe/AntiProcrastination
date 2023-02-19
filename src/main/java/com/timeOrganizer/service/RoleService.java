package com.timeOrganizer.service;

import com.timeOrganizer.exception.RoleNotFoundException;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IActivityRepository;
import com.timeOrganizer.repository.IRoleRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements IRoleService{

    private final IRoleRepository roleRepository;
    private final IActivityRepository activityRepository;

    @Autowired
    public RoleService(IRoleRepository roleRepository, IActivityRepository activityRepository) {
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }
    @Override
    public Role getRoleById(@NotNull Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id));
    }
    @Override
    public Role createRole(String name) {
        return roleRepository.save(new Role(name));
    }
    @Override
    public void deleteRole(@NotNull Long id) {
        roleRepository.deleteById(id);
    }
    @Override
    public Role updateRole(Long id,String newName) {
        Role role = this.getRoleById(id);
        role.setName(newName);
        return roleRepository.save(role);
    }
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
    @Override
    public List<Activity> getActivitiesByRole(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RoleNotFoundException(id);
        }
        return activityRepository.findByRole(role.get());
    }
}
