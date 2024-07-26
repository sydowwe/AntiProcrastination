package com.timeOrganizer.model.dto.request;

import com.timeOrganizer.model.dto.request.extendable.IRequest;
import lombok.Getter;

import java.time.Instant;

@Getter
@SuppressWarnings("unused")
public class WebExtensionDataRequest implements IRequest
{
	private String domain;
	private String title;
	private int duration;
	private Instant startTimestamp;
	private Long activityId;
}
