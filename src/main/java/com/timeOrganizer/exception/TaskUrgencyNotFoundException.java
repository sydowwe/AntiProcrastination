package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskUrgencyNotFoundException extends EntityNotFoundException {
    public TaskUrgencyNotFoundException(Long urgencyId) {
        super("Task urgency with id " + urgencyId + " not found");
    }
}
