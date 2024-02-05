package com.timeOrganizer.service;

import com.timeOrganizer.exception.BatchDeleteException;
import com.timeOrganizer.model.dto.mappers.toDoList.ToDoListMapper;
import com.timeOrganizer.model.dto.request.IdIsDoneRequest;
import com.timeOrganizer.model.dto.request.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.ToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.ToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.repository.IToDoListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ToDoListService extends MyService<ToDoList,IToDoListRepository,ToDoListRequest,ToDoListResponse,ToDoListMapper> implements IToDoListService {
    private final UrgencyService urgencyService;
    @Autowired
    public ToDoListService(IToDoListRepository repository, ToDoListMapper mapper, UrgencyService urgencyService) {
        super(repository, mapper);
        this.urgencyService = urgencyService;
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(ToDoListRequest request) {
        return Map.of("urgency",urgencyService.getReference(request.getUrgencyId()));
    }
    @Override
    protected Sort.Direction getSortDirection(){
        return Sort.Direction.ASC;
    }
    @Override
    protected String getSortByProperties(){
        return "urgency.priority";
    }

    public void setIsDone(IdIsDoneRequest request) throws EntityNotFoundException {
        this.repository.updateDoneById(request.getId(),request.isDone());
    }
    public void batchSetIsDone(List<IdIsDoneRequest> requestList) throws EntityNotFoundException {
        var ids = requestList.stream().map(IdIsDoneRequest::getId).toList();
        List<ToDoList> toDoListItems = this.repository.findAllById(ids);
        if (toDoListItems.size() != ids.size()) {
            throw new EntityNotFoundException();
        }
        toDoListItems.forEach(item->item.setDone(requestList.get(0).isDone()));
        this.repository.saveAll(toDoListItems);
    }

    public void batchDelete(List<IdRequest> idList) throws BatchDeleteException {
        var ids = idList.stream().map(IdRequest::getId).toList();
        int deletedIds = this.repository.batchDelete(ids);
        if (deletedIds != ids.size()) {
            throw new BatchDeleteException();
        }
    }
}
