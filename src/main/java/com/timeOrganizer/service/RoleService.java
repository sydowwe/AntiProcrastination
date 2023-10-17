package com.timeOrganizer.service;

import com.timeOrganizer.exception.RoleNotFoundException;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    @Autowired
    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public Role getRoleById(Long id) throws IllegalArgumentException,RoleNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return roleRepository.findById(id).orElseThrow(()-> new RoleNotFoundException(id));
    }

    @Override
    public Role createRole(NameTextColorIconRequest roleRequest) {
        if (roleRequest == null) {
            throw new IllegalArgumentException("Role request cannot be null");
        }
        Role role = new Role(roleRequest.getName(), roleRequest.getText(), roleRequest.getColor(), roleRequest.getIcon());
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        // Check if the role exists before deleting
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException(id);
        }

        roleRepository.deleteById(id);
    }

    @Override
    public Role updateRole(Long id, NameTextColorIconRequest roleRequest) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Role role = this.getRoleById(id);
        if (roleRequest == null) {
            throw new IllegalArgumentException("New roleRequest cannot be null");
        }
        if (roleRequest.getName() != null && !roleRequest.getName().isEmpty()) {
            role.setName(roleRequest.getName());
        }

        if (roleRequest.getText() != null && !roleRequest.getText().isEmpty()) {
            role.setText(roleRequest.getText());
        }

        if (roleRequest.getColor() != null && !roleRequest.getColor().isEmpty()) {
            role.setColor(roleRequest.getColor());
        }

        if (roleRequest.getIcon() != null && !roleRequest.getIcon().isEmpty()) {
            role.setIcon(roleRequest.getIcon());
        }
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }



}
