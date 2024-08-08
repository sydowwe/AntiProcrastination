package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.ActivityRequest;
import com.timeOrganizer.model.dto.request.extendable.NameTextRequest;
import com.timeOrganizer.model.dto.response.activity.ActivityOptionResponse;
import com.timeOrganizer.model.dto.response.activity.ActivityResponse;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@JsonRequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController extends MyController
{
	private final ActivityService activityService;

	@PostMapping("/get-all-options")
	public ResponseEntity<List<ActivityOptionResponse>> getAllActivities()
	{
		return ResponseEntity.ok(mapToActivityOptionResponseList(activityService.getAllAsResponse()));
	}

	@PostMapping("/get-options-by-role/{roleId}")
	public ResponseEntity<?> getActivitiesByRoleId(@PathVariable Long roleId)
	{
		if (roleId == null || roleId == 0) {
			return ResponseEntity.badRequest().body("roleId must not be null or 0");
		}
		return ResponseEntity.ok(this.mapToActivityOptionResponseList(activityService.getActivitiesByRoleId(roleId)));
	}

	@PostMapping("/get-options-by-category/{categoryId}")
	public ResponseEntity<?> getActivitiesByCategoryId(@PathVariable Long categoryId)
	{
		if (categoryId == null || categoryId == 0) {
			return ResponseEntity.badRequest().body("categoryId must not be null or 0");
		}
		return ResponseEntity.ok(this.mapToActivityOptionResponseList(activityService.getActivitiesByCategoryId(categoryId)));
	}

	//TODO
	@PostMapping("/get-by-activity")
	public ResponseEntity<ActivityResponse> getActivityById(@RequestBody @NonNull Long id)
	{
		return ResponseEntity.ok(activityService.getResponseById(id));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable("id") Long id)
	{
		return ResponseEntity.ok(this.mapToSelectOptionResponse(activityService.getResponseById(id)));
	}

	@PostMapping("/create")
	public ResponseEntity<?> createNewActivity(@RequestBody ActivityRequest formData)
	{
		ActivityResponse newActivity = activityService.insert(formData);
		URI uri = this.getCreatedResourceURI(newActivity.getId());
		return ResponseEntity.created(uri).body(newActivity);
	}

	@PostMapping("/quick-edit/{id}")
	public ResponseEntity<?> quickEditActivity(@PathVariable long id, @RequestBody NameTextRequest quickEditRequest)
	{
		boolean success = activityService.quickEdit(id, quickEditRequest);
		return ResponseEntity.ok(new SuccessResponse("quick edited"));
	}

	public List<ActivityOptionResponse> mapToActivityOptionResponseList(List<? extends ActivityResponse> list)
	{
		return list.stream()
			.map(ActivityOptionResponse::new)
			.collect(Collectors.toList());
	}
}
