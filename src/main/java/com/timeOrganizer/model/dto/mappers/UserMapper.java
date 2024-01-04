package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.UserRequest;
import com.timeOrganizer.model.dto.response.EditedUserResponse;
import com.timeOrganizer.model.dto.response.UserResponse;
import com.timeOrganizer.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserMapper {
    public UserResponse convertToUserSettingsResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(StringUtils.capitalize(user.getName()))
                .surname(StringUtils.capitalize(user.getSurname()))
                .email(user.getEmail())
                .has2FA(user.has2FA())
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
    public User editFromRequest(User user, UserRequest request){
        user.setName(request.getName().toLowerCase());
        user.setSurname(request.getSurname().toLowerCase());
        user.setEmail(request.getEmail());
        if(!request.isHas2FA()){
            user.setSecretKey2FA(null);
            user.setScratchCodes(null);
        }
        return user;
    }
}
