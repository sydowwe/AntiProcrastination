package com.timeOrganizer.security.twoFactorAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.warrenstrange.googleauth.ICredentialRepository;

@Component
public class TwoFactorAuthRepository implements ICredentialRepository {
    private final Map<String, UserTOTP> usersKeys = new HashMap<>();

    @Override
    public void saveUserCredentials(String email,
                                    String secretKey,
                                    int validationCode,
                                    List<Integer> scratchCodes) {
        usersKeys.put(email, new UserTOTP(email, secretKey, validationCode, scratchCodes));
    }
    @Override
    public String getSecretKey(String email) {
        return this.getUser(email).getSecretKey();
    }

    private UserTOTP getUser(String email) {
        return usersKeys.get(email);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private class UserTOTP {
        private String email;
        private String secretKey;
        private int validationCode;
        private List<Integer> scratchCodes;
    }
}
