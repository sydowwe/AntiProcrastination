package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.UserRequest;
import com.timeOrganizer.model.dto.response.EditedUserResponse;
import com.timeOrganizer.model.dto.response.UserResponse;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.security.LoggedUser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserMapper{
    public UserResponse convertToUserSettingsResponse(LoggedUser user){
        return UserResponse.builder()
                .id(user.getId())
                .name(StringUtils.capitalize(user.getName()))
                .surname(StringUtils.capitalize(user.getSurname()))
                .email(user.getEmail())
                .has2FA(user.isHas2FA())
                .build();
    }
    public EditedUserResponse convertToEditedUserSettingsResponse(User user, String token, byte[] qrCode){
        return EditedUserResponse.builder()
                .id(user.getId())
                .name(StringUtils.capitalize(user.getName()))
                .surname(StringUtils.capitalize(user.getSurname()))
                .email(user.getEmail())
                .has2FA(user.has2FA())
                .token(token)
                .qrCode(qrCode)
                .build();
    }
    public LoggedUser toLoggedUser(User reference){
        return LoggedUser.builder()
                .reference(reference)
                .id(reference.getId())
                .email(reference.getEmail())
                .role(reference.getRole())
                .isStayLoggedIn(reference.isStayLoggedIn())
                .has2FA(reference.has2FA())
                .name(reference.getName())
                .surname(reference.getSurname())
                .password(reference.getPassword())
                .build();
    }

    public User updateEntityFromRequest(User entity, UserRequest request) {
        entity.setName(request.getName().toLowerCase());
        entity.setSurname(request.getSurname().toLowerCase());
        entity.setEmail(request.getEmail());
        if(!request.isHas2FA()){
            entity.setSecretKey2FA(null);
            entity.setScratchCodes(null);
        }
        return entity;
    }
}
