package com.timeOrganizer.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.dto.response.extendable.NameTextColorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class AlarmResponse extends NameTextColorResponse
{
	private String startTimestamp;
	private ActivityResponse activity;
	@JsonProperty(value = "isActive")
	private boolean isActive;
}
