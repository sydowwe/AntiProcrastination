package com.timeOrganizer.exception;

public class RequiredParameterMissingException extends IllegalArgumentException  {
    public RequiredParameterMissingException(String message) {
        super(message);
    }
}