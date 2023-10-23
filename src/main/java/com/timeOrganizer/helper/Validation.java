package com.timeOrganizer.helper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Validation {
    private boolean success;
    private String message;
}
