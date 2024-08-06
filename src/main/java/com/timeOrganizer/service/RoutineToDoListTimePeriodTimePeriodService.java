package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.toDoList.TimePeriodMapper;
import com.timeOrganizer.model.dto.request.toDoList.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.toDoList.TimePeriodResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IRoutineToDoListTimePeriodRepository;
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
public class RoutineToDoListTimePeriodTimePeriodService extends MyService<RoutineTimePeriod, IRoutineToDoListTimePeriodRepository, TimePeriodRequest, TimePeriodResponse, TimePeriodMapper> implements IRoutineToDoListTimePeriodService
{
    @Autowired
    public RoutineToDoListTimePeriodTimePeriodService(IRoutineToDoListTimePeriodRepository repository, TimePeriodMapper mapper) {
        super(repository, mapper);
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(TimePeriodRequest request) {
        return null;
    }
    @Override
    protected Sort.Direction getSortDirection(){
        return Sort.Direction.ASC;
    }

    public void changeIsHiddenInView(long id){
        int affectedRows = this.repository.updateIsHiddenInView(id);
    }
    public void createDefaultItems(User user) throws EntityExistsException, RollbackException {
        this.repository.saveAll(
            List.of(
	            RoutineTimePeriod.builder().text("Daily").color("#92F58C").lengthInDays(1).isHiddenInView(false).user(user).build(), // Green
	            RoutineTimePeriod.builder().text("Weekly").color("#936AF1").lengthInDays(7).isHiddenInView(false).user(user).build(),// purple
	            RoutineTimePeriod.builder().text("Monthly").color("#2C7EF4").lengthInDays(30).isHiddenInView(false).user(user).build(),  // blue
	            RoutineTimePeriod.builder().text("Yearly").color("#A5CCF3").lengthInDays(365).isHiddenInView(false).user(user).build()// Grey
            )
        );
    }
}
