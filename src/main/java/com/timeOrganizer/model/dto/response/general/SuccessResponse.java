package com.timeOrganizer.model.dto.response.general;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {
    private String message;
    private final String status = "success";
}
