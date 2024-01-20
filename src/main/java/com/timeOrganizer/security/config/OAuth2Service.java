package com.timeOrganizer.security.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

//    private final OAuth2AuthorizedClientManager authorizedClientManager;

    public void getUserInfo(OAuth2AuthorizationCodeGrantRequest authorizationGrantRequest) {
        var test1 = authorizationGrantRequest.getAuthorizationExchange().getAuthorizationRequest();
        var test2 = authorizationGrantRequest.getAuthorizationExchange().getAuthorizationResponse();
        var authorizationCode = test2.getCode();

        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";

        OAuth2AccessToken accessToken = exchangeAuthorizationCodeForToken(authorizationCode);


        GoogleUserInfo googleUserInfo = new RestTemplate().getForObject(
                userInfoEndpoint + "?access_token=" + accessToken.getTokenValue(),
                GoogleUserInfo.class
        );

        var dsds = 1;
//        // Map GoogleUserInfo to OAuth2User or customize as needed
//        // ...
//
//        return oAuth2User;
    }

    private OAuth2AccessToken exchangeAuthorizationCodeForToken(String authorizationCode) throws RuntimeException{
        // Use RestTemplate or WebClient to exchange authorization code for access token
        // Customize this part based on your OAuth2 provider's token endpoint
        // For simplicity, we'll use RestTemplate to demonstrate the concept
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        String requestBody = "code=" + authorizationCode +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=your_redirect_uri" +
                "&grant_type=authorization_code";

        OAuth2TokenResponse tokenResponse = new RestTemplate().postForObject(tokenEndpoint, requestBody, OAuth2TokenResponse.class);

        if(tokenResponse == null){
            throw new RuntimeException("no token response");
        }
        // Extract and return the access token from the token response
        return new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                tokenResponse.getAccessToken(),
                tokenResponse.getIssuedAtInstant(),
                tokenResponse.getExpiresAtInstant()); // Additional parameters if needed
    }

}