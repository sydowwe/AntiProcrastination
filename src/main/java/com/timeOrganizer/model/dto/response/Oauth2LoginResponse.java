package com.timeOrganizer.model.dto.response;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class Oauth2LoginResponse extends LoginResponse {
    private boolean authenticated;
}
