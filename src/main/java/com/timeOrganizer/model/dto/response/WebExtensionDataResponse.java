package com.timeOrganizer.model.dto.response;

import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class WebExtensionDataResponse extends IdResponse
{
	private String domain;
	private String title;
	private int duration;
	private Instant startTimestamp;
	private Long activityId;
}
