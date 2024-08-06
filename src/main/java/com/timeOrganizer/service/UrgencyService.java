package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.toDoList.UrgencyMapper;
import com.timeOrganizer.model.dto.request.toDoList.UrgencyRequest;
import com.timeOrganizer.model.dto.response.toDoList.UrgencyResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.TaskUrgency;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUrgencyRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UrgencyService extends MyService<TaskUrgency, IUrgencyRepository, UrgencyRequest, UrgencyResponse, UrgencyMapper> implements IUrgencyService
{
    @Autowired
    public UrgencyService(IUrgencyRepository repository, UrgencyMapper mapper) {
        super(repository, mapper);
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(UrgencyRequest request) {
        return null;
    }
    @Override
    protected Sort.Direction getSortDirection(){
        return Sort.Direction.ASC;
    }
    @Override
    protected String getSortByProperties(){
        return "priority";
    }

    @Override
    public void createDefaultItems(User user) throws EntityExistsException, RollbackException {
        this.repository.saveAll(
            List.of(
	            TaskUrgency.builder().text("Today").color("#FF5252").priority(1).user(user).build(), // Red
	            TaskUrgency.builder().text("This week").color("#FFA726").priority(2).user(user).build(),// Orange
	            TaskUrgency.builder().text("This month").color("#FFD600").priority(3).user(user).build(), // Yellow
	            TaskUrgency.builder().text("This year").color("#4CAF50").priority(4).user(user).build() // Green
            )
        );
    }
}
