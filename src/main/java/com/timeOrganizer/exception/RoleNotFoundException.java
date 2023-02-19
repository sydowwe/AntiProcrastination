package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class RoleNotFoundException extends EntityNotFoundException {
    public RoleNotFoundException(Long roleId) {
        super("Role with id " + roleId + " not found");
    }
}
