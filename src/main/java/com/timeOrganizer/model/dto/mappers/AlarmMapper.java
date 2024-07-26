package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.AlarmRequest;
import com.timeOrganizer.model.dto.response.AlarmResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AlarmMapper extends AbstractInOutMapper<Alarm, AlarmRequest, AlarmResponse>
{
	private final ActivityMapper activityMapper;

	@Override
	public AlarmResponse convertToFullResponse(Alarm alarm)
	{
		return AlarmResponse.builder()
			.id(alarm.getId())
			.startTimestamp(alarm.getStartTimestamp().toString())
			.isActive(alarm.isActive())
			.activity(
				alarm.getActivity() != null
					? activityMapper.convertToFullResponse(alarm.getActivity())
					: null
			)
			.build();
	}

	@Override
	public Alarm updateEntityFromRequest(Alarm entity, AlarmRequest request, Map<String, ? extends AbstractEntity> dependencies)
	{
		entity.setStartTimestamp(request.getStartTimestamp());
		entity.setActive(request.isActive());
		entity.setActivity(
			!dependencies.isEmpty() && dependencies.containsKey("activity")
				? (Activity) dependencies.get("activity")
				: null
		);
		return entity;
	}

	@Override
	protected Alarm createEmptyEntity()
	{
		return new Alarm();
	}
}
