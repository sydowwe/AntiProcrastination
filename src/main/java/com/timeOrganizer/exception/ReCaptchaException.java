package com.timeOrganizer.exception;

public class ReCaptchaException extends RuntimeException
{
	public ReCaptchaException()
	{
		super("ReCaptcha v3 failed! Reason: Token or Action mismatch");
	}
}