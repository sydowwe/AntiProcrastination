package com.timeOrganizer.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String email){
        super("User with email: " + email + " not found");
    }
}
