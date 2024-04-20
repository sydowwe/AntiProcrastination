package com.timeOrganizer.model.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LockedOutResponse
{
	private final String status = "lockedOut";
	private int seconds;
}
