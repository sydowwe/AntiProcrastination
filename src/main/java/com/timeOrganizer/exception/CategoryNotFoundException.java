package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
    public CategoryNotFoundException(Long categoryId) {
        super("Category with id " + categoryId + " not found");
    }
}
