package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class NoActivityFoundWithParameterException extends EntityNotFoundException {
    public NoActivityFoundWithParameterException(String parameterSearchedBy, String value) {
        super("To-do list item with" + parameterSearchedBy + " = " + value + " not found");
    }
}

