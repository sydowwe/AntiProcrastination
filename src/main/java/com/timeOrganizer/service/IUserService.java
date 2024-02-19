package com.timeOrganizer.service;

import com.timeOrganizer.exception.QrCode2FAGenerationException;
import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.model.dto.request.user.GoogleAuthLoginRequest;
import com.timeOrganizer.model.dto.request.user.LoginRequest;
import com.timeOrganizer.model.dto.request.user.RegistrationRequest;
import com.timeOrganizer.model.dto.request.user.UserRequest;
import com.timeOrganizer.model.dto.response.user.*;
import com.timeOrganizer.security.LoggedUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface IUserService
{
	RegistrationResponse registerUser(RegistrationRequest registration) throws PersistenceException, QrCode2FAGenerationException;
	Oauth2LoginResponse oauth2LoginUser(OAuth2User oAuth2User) throws AuthenticationException, UserNotFoundException;
	LoginResponse loginUser(LoginRequest loginRequest) throws AuthenticationException, UserNotFoundException;
	GoogleAuthResponse validate2FALogin(GoogleAuthLoginRequest request) throws UserNotFoundException;
	void logout(String token);
	void resetPassword(String email) throws UserNotFoundException;
	boolean wereSensitiveChangesMade(LoggedUser loggedUser, UserRequest changedUser);
	boolean verifyPasswordAndReturn2FAStatus(String token, String password) throws AuthenticationException;
	boolean validate2FA(String token, int code) throws EntityNotFoundException, NumberFormatException;
	UserResponse getLoggedUserData(LoggedUser loggedUser);
	EditedUserResponse editLoggedUserData(String token, UserRequest request) throws EntityNotFoundException, NumberFormatException,
		QrCode2FAGenerationException;
	void changeLoggedUserPassword(String token, String newPassword) throws EntityNotFoundException ;
	void deleteLoggedUserAccount(String token) throws EntityNotFoundException, NumberFormatException;
	byte[] get2FAQrCode(String token) throws EntityNotFoundException, NumberFormatException, QrCode2FAGenerationException;
	int get2FAScratchCode(String token) throws EntityNotFoundException, NumberFormatException, QrCode2FAGenerationException;

}
