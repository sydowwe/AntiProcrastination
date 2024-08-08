package com.timeOrganizer.model.dto.response.history;

import com.timeOrganizer.model.dto.response.general.SelectOptionResponse;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class HistoryFilterSelectsResponse
{
	private List<SelectOptionResponse> activityOptions;
	private List<SelectOptionResponse> roleOptions;
	private List<SelectOptionResponse> categoryOptions;
}
