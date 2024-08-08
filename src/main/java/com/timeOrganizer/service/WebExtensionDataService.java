package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.WebExtensionDataMapper;
import com.timeOrganizer.model.dto.request.WebExtensionDataRequest;
import com.timeOrganizer.model.dto.response.WebExtensionDataResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.WebExtensionData;
import com.timeOrganizer.repository.IWebExtensionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class WebExtensionDataService extends MyService<WebExtensionData, IWebExtensionDataRepository, WebExtensionDataRequest, WebExtensionDataResponse, WebExtensionDataMapper>
{
	private final ActivityService activityService;

	@Autowired
	public WebExtensionDataService(IWebExtensionDataRepository repository, WebExtensionDataMapper mapper, ActivityService activityService)
	{
		super(repository, mapper);
		this.activityService = activityService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(WebExtensionDataRequest request)
	{
		return this.getMapFromDependencies(activityService.getReference(request.getActivityId()));
	}
}
