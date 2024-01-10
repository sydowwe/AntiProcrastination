package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.ToDoListMapper;
import com.timeOrganizer.model.dto.request.ToDoListRequest;
import com.timeOrganizer.model.dto.response.ToDoListResponse;
import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.repository.IToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ToDoListService extends MyService<ToDoList,IToDoListRepository,ToDoListResponse,ToDoListRequest,ToDoListMapper> implements IToDoListService {
    private final UrgencyService urgencyService;
    @Autowired
    public ToDoListService(IToDoListRepository repository, ToDoListMapper mapper, UserService userService, UrgencyService urgencyService) {
        super(repository, mapper, userService);
        this.urgencyService = urgencyService;
    }
}
