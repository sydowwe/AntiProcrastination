package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.WebExtensionDataRequest;
import com.timeOrganizer.model.dto.response.WebExtensionDataResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.WebExtensionData;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebExtensionDataMapper extends AbstractInOutMapper<WebExtensionData, WebExtensionDataRequest, WebExtensionDataResponse>
{
	@Override
	public WebExtensionDataResponse convertToFullResponse(WebExtensionData data)
	{
		return WebExtensionDataResponse.builder()
			.id(data.getId())
			.domain(data.getDomain())
			.title(data.getTitle())
			.startTimestamp(data.getStartTimestamp())
			.duration(data.getDuration())
			.activityId(data.getActivity().getId()).build();
	}

	@Override
	public WebExtensionData updateEntityFromRequest(WebExtensionData entity, WebExtensionDataRequest request, Map<String, ? extends AbstractEntity> dependencies)
	{
		entity.setDomain(request.getDomain());
		entity.setTitle(request.getTitle());
		entity.setStartTimestamp(request.getStartTimestamp());
		entity.setDuration(request.getDuration());
		entity.setActivity((Activity) dependencies.get("activity"));
		return entity;
	}

	@Override
	protected WebExtensionData createEmptyEntity()
	{
		return new WebExtensionData();
	}
}