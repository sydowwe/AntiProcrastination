package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.request.activity.ActivitySelectForm;
import com.timeOrganizer.model.dto.request.extendable.NameTextRequest;
import com.timeOrganizer.model.dto.response.activity.ActivityFormSelectsResponse;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.repository.IActivityRepository;
import com.timeOrganizer.specifications.ActivitySpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ActivityService extends MyService<Activity, IActivityRepository, ActivityRequest, ActivityResponse, ActivityMapper> implements IActivityService
{
	private final RoleService roleService;
	private final CategoryService categoryService;
	private final IActivityRepository iActivityRepository;

	@Autowired
	public ActivityService(
		IActivityRepository repository, ActivityMapper mapper, RoleService roleService, CategoryService categoryService,
		IActivityRepository iActivityRepository
	)
	{
		super(repository, mapper);
		this.roleService = roleService;
		this.categoryService = categoryService;
		this.iActivityRepository = iActivityRepository;
	}

	@Override
	protected Map<String, ? extends AbstractEntity> getDependencies(ActivityRequest request)
	{
		return request.getCategoryId() != null ? this.getMapFromDependencies(this.roleService.getReference(request.getRoleId()), this.categoryService.getReference(request.getCategoryId()))
			: this.getMapFromDependencies(this.roleService.getReference(request.getRoleId()));
	}


	public ActivityFormSelectsResponse updateFilterSelects(ActivitySelectForm request)
	{
		Specification<Activity> spec = ActivitySpecifications.withFilter(
			UserService.getLoggedUser().getId(),
			request.getActivityId(),
			request.getRoleId(),
			request.getCategoryId(),
			request.getIsFromToDoList(),
			request.getIsUnavoidable()
		);
		return this.getActivityFormSelectsFromActivityList(this.repository.findAll(spec));
	}

	public ActivityFormSelectsResponse getActivityFormSelectsFromActivityList(List<Activity> activityList)
	{
		return ActivityFormSelectsResponse.builder()
			.activityOptions(mapper.convertToFullResponseList(activityList))
			.categoryOptions(super.getDistinctOptionList(activityList, Activity::getCategory))
			.roleOptions(super.getDistinctOptionList(activityList, Activity::getRole))
			.build();
	}

	public ActivityFormSelectsResponse updateFilterSelectsForNew()
	{
		return ActivityFormSelectsResponse.builder()
			.activityOptions(null)
			.categoryOptions(this.getOptionResponseFromList(categoryService.getAll()))
			.roleOptions(this.getOptionResponseFromList(roleService.getAll()))
			.build();
	}
	public boolean quickEdit(long id, NameTextRequest request)
	{
		Activity activity = this.getById(id);
		//TODO DOKOncit quickedit
//		activity.setName(request.getName());
//		activity.setText(request.getText());
		try {
			iActivityRepository.save(activity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
