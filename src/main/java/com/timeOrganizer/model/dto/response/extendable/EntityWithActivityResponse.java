package com.timeOrganizer.model.dto.response.extendable;

import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class EntityWithActivityResponse extends IdResponse
{
	private ActivityResponse activity;
}
