package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.TimePeriodMapper;
import com.timeOrganizer.model.dto.request.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.TimePeriodResponse;
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
public class RoutineToDoListTimePeriodService extends MyService<RoutineTimePeriod, IRoutineToDoListTimePeriodRepository, TimePeriodRequest, TimePeriodResponse, TimePeriodMapper> {
    @Autowired
    public RoutineToDoListTimePeriodService(IRoutineToDoListTimePeriodRepository repository, TimePeriodMapper mapper) {
        super(repository, mapper);
    }
    public void createDefaultItems(User user) throws EntityExistsException, RollbackException {
        this.repository.saveAll(
                List.of(
                        new RoutineTimePeriod("Daily", "#92F58C", 1, user),         // Green
                        new RoutineTimePeriod("Weekly", "#936AF1", 7, user),      // purple
                        new RoutineTimePeriod("Monthly", "#2C7EF4", 30, user),     // blue
                        new RoutineTimePeriod("Yearly", "#A5CCF3", 365, user)       // Grey
                )
        );
    }
    @Override
    protected Sort.Direction getSortDirection(){
        return Sort.Direction.ASC;
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(TimePeriodRequest request) {
        return null;
    }
}
