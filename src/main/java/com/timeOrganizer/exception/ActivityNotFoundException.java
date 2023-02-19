package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class ActivityNotFoundException extends EntityNotFoundException {
    public ActivityNotFoundException(Long activityId) {
        super("Activity with id " + activityId + " not found");
    }
}
