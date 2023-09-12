package com.timeOrganizer.service;

import com.timeOrganizer.exception.ToDoListNotFoundException;
import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.model.entity.Urgency;
import com.timeOrganizer.repository.IToDoListRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ToDoListService implements IToDoListService {
    private final IToDoListRepository toDoListRepository;
    private final UrgencyService urgencyService;

    @Autowired
    public ToDoListService(IToDoListRepository toDoListRepository, UrgencyService urgencyService) {
        this.toDoListRepository = toDoListRepository;
        this.urgencyService = urgencyService;
    }

    @Override
    public ToDoList addToDoListItem(ToDoListRequest toDoListRequest) {
        Urgency urgency = urgencyService.getUrgencyItemById(toDoListRequest.getUrgencyId());
        ToDoList toDoList = new ToDoList(toDoListRequest.getName(),toDoListRequest.getText(),toDoListRequest.isDone(),urgency);
        return toDoListRepository.save(toDoList);
    }

    @Override
    public void deleteToDoListItem(@NotNull Long id) {
        toDoListRepository.deleteById(id);
    }

    @Override
    public ToDoList updateToDoListItem(Long id, @NotNull ToDoListRequest toDoListRequest) {
        ToDoList toDoList = this.getToDoListItemById(id);
        return toDoListRepository.save(toDoList);
    }

    @Override
    public ToDoList getToDoListItemById(Long id) {
        return toDoListRepository.findById(id)
                .orElseThrow(() -> new ToDoListNotFoundException(id));
    }

    @Override
    public List<ToDoList> getAllToDoListItems() {
        return toDoListRepository.findAll();
    }

   /* @Override
    public List<Activity> getActivitiesByToDoListUrgency(Long urgencyId) {
        List<ToDoList> toDoListItems = toDoListRepository.findByUrgency(urgencyId);
        var test = toDoListItems.stream().map(item -> item.getActivity().getId()).toList();
        return activityRepository.findAllById(test);
    }*/

}
