package com.timeOrganizer.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleAuthResponse {
    private boolean authorized;
    private String token;
}
