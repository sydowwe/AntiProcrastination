package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.taskPlanner.PlannerTaskRequest;
import com.timeOrganizer.model.dto.response.PlannerTaskResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.PlannerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PlannerTaskMapper extends AbstractInOutMapper<PlannerTask, PlannerTaskRequest, PlannerTaskResponse> {
	private final ActivityMapper activityMapper;

	@Override
	public PlannerTaskResponse convertToFullResponse(PlannerTask plannerTask) {
		return PlannerTaskResponse.builder()
			.id(plannerTask.getId())
			.startTimestamp(plannerTask.getStartTimestamp().toString())
			.minuteLength(plannerTask.getMinuteLength())
			.isDone(plannerTask.isDone())
			.activity(
				plannerTask.getActivity() != null
					? activityMapper.convertToFullResponse(plannerTask.getActivity())
					: null
			)
			.build();
	}

	@Override
	public PlannerTask updateEntityFromRequest(PlannerTask entity, PlannerTaskRequest request, Map<String, ? extends AbstractEntity> dependencies) {
		entity.setStartTimestamp(request.getStartTimestamp());
		entity.setMinuteLength(request.getMinuteLength());
		entity.setDone(request.isDone());
		entity.setActivity(this.getEntityFromDependencies(Activity.class, dependencies));
		return entity;
	}

	@Override
	protected PlannerTask createEmptyEntity() {
		return new PlannerTask();
	}
}
