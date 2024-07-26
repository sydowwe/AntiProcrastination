package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.AlarmRequest;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.response.AlarmResponse;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController extends MyController
{
	private final AlarmService alarmService;

	//	@PostMapping("/apply-filter")
//	public ResponseEntity<List<AlarmResponse>> applyFilter(@RequestBody PlannerFilterRequest request) {
//		return ResponseEntity.ok(alarmService.getAllByDateAndHourSpan(this.getLoggedUser().getId(),request));
//	}
//	@GetMapping("/{id}")
//	public ResponseEntity<SelectOptionResponse> get(@PathVariable("id") Long id) {
//		return ResponseEntity.ok(this.mapToSelectOptionResponse(alarmService.getResponseById(id)));
//	}
	@PostMapping("/get-all")
	public ResponseEntity<List<AlarmResponse>> getAll()
	{
		return ResponseEntity.ok(alarmService.getAllAsResponse(this.getLoggedUser().getId()));
	}

	@PatchMapping("/change-active")
	public ResponseEntity<SuccessResponse> changeActive(@RequestBody List<IdRequest> requestList)
	{
		alarmService.setIsDone(requestList);
		return ResponseEntity
			.ok(new SuccessResponse("changed"));
	}

	@PostMapping("/add")
	public ResponseEntity<AlarmResponse> addAlarm(@RequestBody AlarmRequest request)
	{
		AlarmResponse newAlarm = alarmService.insert(request, this.getLoggedUser().getReference());
		return ResponseEntity
			.created(this.getCreatedResourceURI(newAlarm.getId()))
			.body(newAlarm);
	}

	@PutMapping("/{id}")
	public ResponseEntity<AlarmResponse> editAlarm(@PathVariable("id") Long id, @RequestBody AlarmRequest request)
	{
		return ResponseEntity.ok(alarmService.updateById(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<IdResponse> deleteAlarm(@PathVariable("id") Long id)
	{
		alarmService.deleteById(id);
		return ResponseEntity.ok(new IdResponse(id));
	}

	@PostMapping("/batch-delete")
	public ResponseEntity<SuccessResponse> batchDelete(@RequestBody List<IdRequest> request)
	{
		alarmService.batchDelete(request);
		return ResponseEntity
			.ok(new SuccessResponse("deleted"));
	}
}
