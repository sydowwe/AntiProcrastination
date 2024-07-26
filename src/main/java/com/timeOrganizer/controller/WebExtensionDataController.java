package com.timeOrganizer.controller;

import com.timeOrganizer.helper.JsonRequestMapping;
import com.timeOrganizer.model.dto.request.WebExtensionDataRequest;
import com.timeOrganizer.model.dto.response.WebExtensionDataResponse;
import com.timeOrganizer.service.WebExtensionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@JsonRequestMapping("/web-extension")
@RequiredArgsConstructor
public class WebExtensionDataController extends MyController
{
	private final WebExtensionDataService webExtensionDataService;

	@PostMapping("/get-all")
	public ResponseEntity<List<WebExtensionDataResponse>> getAllOptions()
	{
		return ResponseEntity.ok(webExtensionDataService.getAllAsResponse(this.getLoggedUser().getId()));
	}

	@PostMapping("/create")
	public ResponseEntity<WebExtensionDataResponse> createNewRole(@RequestBody WebExtensionDataRequest newData)
	{
		var response = webExtensionDataService.insert(newData, getLoggedUser().getReference());
		URI uri = this.getCreatedResourceURI(response.getId());
		return ResponseEntity.created(uri).body(response);
	}
}
