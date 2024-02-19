package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.extendable.IdRequest;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface IToDoListService {
	void setIsDone(List<IdRequest> requestList) throws EntityNotFoundException;
}
