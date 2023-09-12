package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class ToDoListNotFoundException extends EntityNotFoundException {
    public ToDoListNotFoundException(Long toDoListItemId) {
        super("To-do list item with id " + toDoListItemId + " not found");
    }
}
