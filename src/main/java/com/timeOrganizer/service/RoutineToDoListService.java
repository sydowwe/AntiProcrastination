package com.timeOrganizer.service;

import com.timeOrganizer.exception.BatchDeleteException;
import com.timeOrganizer.model.dto.mappers.toDoList.RoutineToDoListMapper;
import com.timeOrganizer.model.dto.request.IdIsDoneRequest;
import com.timeOrganizer.model.dto.request.IdRequest;
import com.timeOrganizer.model.dto.request.toDoList.RoutineToDoListRequest;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListGroupedResponse;
import com.timeOrganizer.model.dto.response.toDoList.RoutineToDoListResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import com.timeOrganizer.model.entity.RoutineToDoList;
import com.timeOrganizer.repository.IRoutineToDoListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoutineToDoListService extends MyService<RoutineToDoList, IRoutineToDoListRepository, RoutineToDoListRequest, RoutineToDoListResponse, RoutineToDoListMapper> implements IToDoListService {
    private final RoutineToDoListTimePeriodService timePeriodService;
    @Autowired
    public RoutineToDoListService(IRoutineToDoListRepository repository, RoutineToDoListMapper mapper, RoutineToDoListTimePeriodService timePeriodService) {
        super(repository, mapper);
        this.timePeriodService = timePeriodService;
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(RoutineToDoListRequest request) {
        return Map.of("timePeriod",timePeriodService.getReference(request.getTimePeriodId()));
    }

    public void setIsDone(IdIsDoneRequest request) throws EntityNotFoundException {
        if (this.repository.updateDoneById(request.getId(),request.isDone()) < 1){
            throw new EntityNotFoundException();
        }
    }
    public void batchSetIsDone(List<IdIsDoneRequest> requestList) throws EntityNotFoundException {
        var ids = requestList.stream().map(IdIsDoneRequest::getId).toList();
        List<RoutineToDoList> toDoListItems = this.repository.findAllById(ids);
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
    public List<RoutineToDoListGroupedResponse> getAllByUserIdGroupedByTimePeriod(long userId){
        var allItems = this.repository.findAllByUserId(userId,this.getSort());
        Map<RoutineTimePeriod, List<RoutineToDoList>> groupedByTimePeriod = allItems.stream()
                .collect(Collectors.groupingBy(RoutineToDoList::getTimePeriod));
        return this.mapper.convertToGroupedResponseAndSort(groupedByTimePeriod);
    }
}