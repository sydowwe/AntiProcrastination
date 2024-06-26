package com.timeOrganizer.security.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoogleUserInfo {
    private String name;
    private String surname;
    private String email;
    private boolean isStayLoggedIn;
}
