package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.TimePeriodMapper;
import com.timeOrganizer.model.dto.request.TimePeriodRequest;
import com.timeOrganizer.model.dto.response.TimePeriodResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.RoutineTimePeriod;
import com.timeOrganizer.repository.IRoutineToDoListTimePeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class RoutineToDoListTimePeriodService extends MyService<RoutineTimePeriod, IRoutineToDoListTimePeriodRepository, TimePeriodRequest, TimePeriodResponse, TimePeriodMapper> {
    @Autowired
    public RoutineToDoListTimePeriodService(IRoutineToDoListTimePeriodRepository repository, TimePeriodMapper mapper, UserService userService) {
        super(repository, mapper, userService);
    }

    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(TimePeriodRequest request) {
        return null;
    }
}
