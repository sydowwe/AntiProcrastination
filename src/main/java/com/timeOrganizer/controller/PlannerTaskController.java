package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.PlannerTaskRequest;
import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import com.timeOrganizer.model.dto.response.PlannerTaskResponse;
import com.timeOrganizer.model.dto.response.extendable.IdResponse;
import com.timeOrganizer.model.dto.response.general.IdLabelResponse;
import com.timeOrganizer.model.dto.response.general.SuccessResponse;
import com.timeOrganizer.service.PlannerTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonRequestMapping("/planner")
@RequiredArgsConstructor
public class PlannerTaskController extends MyController{
	private final PlannerTaskService plannerTaskService;
	@PostMapping("/get-all")
	public ResponseEntity<List<PlannerTaskResponse>> getAllPlannerTasks() {
		return ResponseEntity.ok(plannerTaskService.getAll(this.getLoggedUser().getId()));
	}
	@GetMapping("/{id}")
	public ResponseEntity<IdLabelResponse> get(@PathVariable("id") Long id) {
		return ResponseEntity.ok(this.mapToIdNameResponse(plannerTaskService.getResponseById(id)));
	}

	@PostMapping("/add")
	public ResponseEntity<PlannerTaskResponse> addPlannerTask(@RequestBody PlannerTaskRequest request) {
		PlannerTaskResponse newPlannerTask = plannerTaskService.insert(request, this.getLoggedUser().getReference());
		return ResponseEntity
			.created(this.getCreatedResourceURI(newPlannerTask.getId()))
			.body(newPlannerTask);
	}
	@PutMapping("/{id}")
	public ResponseEntity<PlannerTaskResponse> editPlannerTask(@PathVariable("id") Long id, @RequestBody PlannerTaskRequest request) {
		return ResponseEntity.ok(plannerTaskService.updateById(id, request));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<IdResponse> deletePlannerTask(@PathVariable("id") Long id) {
		plannerTaskService.deleteById(id);
		return ResponseEntity.ok(new IdResponse(id));
	}
	@PostMapping("/batch-delete")
	public ResponseEntity<SuccessResponse> batchDelete(@RequestBody List<IdRequest> request) {
		plannerTaskService.batchDelete(request);
		return ResponseEntity
			.ok(new SuccessResponse("deleted"));
	}
}
