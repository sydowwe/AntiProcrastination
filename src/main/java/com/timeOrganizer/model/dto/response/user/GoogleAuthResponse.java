package com.timeOrganizer.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleAuthResponse {
    private boolean authorized;
    private String token;
}
