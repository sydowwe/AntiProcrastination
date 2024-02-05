package com.timeOrganizer.exception;

import org.springframework.dao.DataAccessException;

public class BatchDeleteException extends DataAccessException {
    public BatchDeleteException() {
        super("Not all entities were deleted successfully.");
    }
}