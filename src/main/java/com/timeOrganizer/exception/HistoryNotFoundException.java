package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class HistoryNotFoundException extends EntityNotFoundException {
    public HistoryNotFoundException(Long historyId) {
        super("History with id " + historyId + " not found");
    }
}
