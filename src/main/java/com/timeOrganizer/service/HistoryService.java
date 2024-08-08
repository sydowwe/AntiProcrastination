package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.HistoryMapper;
import com.timeOrganizer.model.dto.request.history.HistoryFilterRequest;
import com.timeOrganizer.model.dto.request.history.HistoryFilterSelectsRequest;
import com.timeOrganizer.model.dto.request.history.HistoryRequest;
import com.timeOrganizer.model.dto.response.history.HistoryFilterSelectsResponse;
import com.timeOrganizer.model.dto.response.history.HistoryListGroupedByDateResponse;
import com.timeOrganizer.model.dto.response.history.HistoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.History;
import com.timeOrganizer.repository.HistorySpecifications;
import com.timeOrganizer.repository.IHistoryRepository;
import com.timeOrganizer.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoryService extends MyService<History, IHistoryRepository, HistoryRequest, HistoryResponse, HistoryMapper> implements IHistoryService
{
	private final ActivityService activityService;

	@Autowired
	public HistoryService(IHistoryRepository repository, HistoryMapper mapper, ActivityService activityService)
	{
		super(repository, mapper);
		this.activityService = activityService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(HistoryRequest request)
	{
		return this.getMapFromDependencies(activityService.getReference(request.getActivityId()));
	}
	@Override
	public List<HistoryListGroupedByDateResponse> filter(HistoryFilterRequest filterRequest)
	{
		LoggedUser loggedUser = UserService.getLoggedUser();
		Specification<History> spec = HistorySpecifications.withFilter(
			loggedUser.getId(),
			filterRequest.getActivityId(),
			filterRequest.getRoleId(),
			filterRequest.getCategoryId(),
			filterRequest.getIsFromToDoList(),
			filterRequest.getIsUnavoidable(),
			filterRequest.getDateFrom(),
			filterRequest.getDateTo(),
			filterRequest.getHoursBack()
		);
		List<HistoryResponse> historyResponses = this.mapper.convertToFullResponseList(this.repository.findAll(spec));
		return historyResponses.stream()
			.collect(Collectors.groupingBy(hr -> hr.getStartTimestamp().atZone(loggedUser.getTimezone()).toLocalDate()))
			.entrySet().stream()
			.map(entry -> new HistoryListGroupedByDateResponse(entry.getKey(), entry.getValue())).sorted(Comparator.comparing(HistoryListGroupedByDateResponse::date)).toList();
    }

	public HistoryFilterSelectsResponse updateFilterSelects(HistoryFilterSelectsRequest request)
	{
		Specification<History> spec = HistorySpecifications.withFilter(
			UserService.getLoggedUser().getId(),
			request.getActivityId(),
			request.getRoleId(),
			request.getCategoryId());
		var list = this.repository.findAll(spec);
		return HistoryFilterSelectsResponse.builder().activityOptions(super.getDistinctOptionList(list, History::getActivity))
			.categoryOptions(super.getDistinctOptionList(list, History -> History.getActivity().getCategory()))
			.roleOptions(super.getDistinctOptionList(list, History -> History.getActivity().getRole())).build();
	}
}
