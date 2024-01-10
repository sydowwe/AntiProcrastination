package com.timeOrganizer.exception;

import lombok.Getter;

@Getter
public class UserNotInSecurityContext extends RuntimeException {
    private final String message = "Failed to get user from SecurityContext";
}
