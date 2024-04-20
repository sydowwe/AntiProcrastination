package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.ActivityMapper;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.request.extendable.NameTextRequest;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.repository.IActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
		return request.getCategoryId() != null ? Map.of("role", this.roleService.getReference(request.getRoleId()), "category", this.categoryService.getReference(request.getCategoryId()))
			: Map.of("role", this.roleService.getReference(request.getRoleId()));
	}

	@Override
	public List<ActivityResponse> getActivitiesByRoleId(long roleId, long userId)
	{
		return this.mapper.convertToFullResponseList(this.repository.findByRoleIdAndUserId(roleId, userId));
	}

	@Override
	public List<ActivityResponse> getActivitiesByCategoryId(long categoryId, long userId)
	{
		return this.mapper.convertToFullResponseList(this.repository.findByCategoryIdAndUserId(categoryId, userId));
	}

	public boolean quickEdit(long id, NameTextRequest request)
	{
		Activity activity = this.getById(id);
		activity.setName(request.getName());
		activity.setText(request.getText());
		try {
			iActivityRepository.save(activity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
