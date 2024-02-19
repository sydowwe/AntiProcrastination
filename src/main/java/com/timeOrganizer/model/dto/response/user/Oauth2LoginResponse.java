package com.timeOrganizer.model.dto.response.user;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class Oauth2LoginResponse extends LoginResponse {
    private boolean authenticated;
}
