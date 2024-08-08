package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.AlarmMapper;
import com.timeOrganizer.model.dto.request.AlarmRequest;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.response.AlarmResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Alarm;
import com.timeOrganizer.repository.IAlarmRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlarmService extends MyService<Alarm, IAlarmRepository, AlarmRequest, AlarmResponse, AlarmMapper>
{
	private final ActivityService activityService;

	@Autowired
	public AlarmService(IAlarmRepository repository, AlarmMapper mapper, ActivityService activityService)
	{
		super(repository, mapper);
		this.activityService = activityService;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(AlarmRequest request)
	{
		return request.getActivityId() != null ? this.getMapFromDependencies(activityService.getReference(request.getActivityId())) : Map.of();
	}

	@Override
	protected Sort.Direction getSortDirection()
	{
		return Sort.Direction.ASC;
	}

	@Override
	protected String getSortByProperties()
	{
		return "start_timestamp";
	}

	//	public List<AlarmResponse> getAllByDateAndHourSpan(long userId,PlannerFilterRequest request){
//		return mapper.convertToFullResponseList(repository.getAllByDateAndHourSpan(userId,request.getFilterDate(),request.getFilterDate().plusSeconds(request.getHourSpan()*3600L));
//	}
	public void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException
	{
		int affectedRows = this.repository.updateIsActiveByIds(requestList.stream().map(IdRequest::getId).toList());
		if (affectedRows <= 0) {
			//throw new UpdateFailedException();
		}
	}
}