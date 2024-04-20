package com.timeOrganizer.helper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FailedLoginLockOutService
{
	private static final int MAX_ATTEMPTS = 5;
	private static final long LOCKOUT_DURATION = 20 ;

	private final HttpSession session;
	public void loginSucceeded(String email) {
		session.removeAttribute(email);
	}
	public void addAttempt(String email) {
		Integer attempts = (Integer) session.getAttribute(email);
		if (attempts == null) {
			attempts = 0;
		}
		if (attempts % MAX_ATTEMPTS == 0 ) {
			Executors.newSingleThreadScheduledExecutor().schedule(() -> {
				session.removeAttribute(email);
			}, LOCKOUT_DURATION, TimeUnit.SECONDS);
		}
		attempts++;
		session.setAttribute(email, attempts);

	}
	public boolean isUserLocked(String email) {
		Integer attempts = (Integer) session.getAttribute(email);
		return attempts != null && attempts >= MAX_ATTEMPTS;
	}
}
