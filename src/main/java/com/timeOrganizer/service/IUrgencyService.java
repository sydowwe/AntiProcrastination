package com.timeOrganizer.service;

import com.timeOrganizer.model.entity.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.RollbackException;

public interface IUrgencyService {
    void createDefaultItems(User user) throws EntityExistsException, RollbackException;
}
