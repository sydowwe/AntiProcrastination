package com.timeOrganizer.service;

import com.timeOrganizer.exception.ToDoListNotFoundException;
import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.model.entity.Urgency;
import com.timeOrganizer.repository.IToDoListRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoListService implements IToDoListService {
    private final IToDoListRepository toDoListRepository;
    private final UrgencyService urgencyService;

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



}
