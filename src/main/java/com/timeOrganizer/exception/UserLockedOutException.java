package com.timeOrganizer.exception;


import lombok.Getter;

public class UserLockedOutException extends RuntimeException
{
	@Getter
	private int seconds;
	public UserLockedOutException(int seconds)
	{
		super("Too many attempts, locked out!");
		this.seconds = seconds;
	}
}
