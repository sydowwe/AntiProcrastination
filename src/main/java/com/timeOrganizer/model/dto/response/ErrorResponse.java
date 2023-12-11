package com.timeOrganizer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse implements IResponse{
    private String title;
    private String message;
}