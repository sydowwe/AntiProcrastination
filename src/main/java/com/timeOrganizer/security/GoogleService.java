package com.timeOrganizer.security;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GoogleService
{

	private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

	public boolean verifyRecaptcha(String token, String expectedAction)
	{
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("secret", System.getenv("RECAPTCHA_SECRET"));
		params.add("response", token);

		ResponseEntity<Map> responseEntity = restTemplate.postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL, params, Map.class);
		Map<String, Object> responseBody = responseEntity.getBody();

		if (responseBody != null) {
			Boolean success = (Boolean) responseBody.get("success");
			Double score = (Double) responseBody.get("score");
			String action = (String) responseBody.get("action");
			return success != null && success && score != null && score >= 0.7 && expectedAction.equals(action);
		}
		return false;
	}
}
