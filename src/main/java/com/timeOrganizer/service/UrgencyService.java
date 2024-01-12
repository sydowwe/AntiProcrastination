package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.UrgencyMapper;
import com.timeOrganizer.model.dto.request.UrgencyRequest;
import com.timeOrganizer.model.dto.response.UrgencyResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Urgency;
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
public class UrgencyService extends MyService<Urgency,IUrgencyRepository, UrgencyResponse, UrgencyRequest,UrgencyMapper> implements IUrgencyService {
    @Autowired
    public UrgencyService(IUrgencyRepository repository, UrgencyMapper mapper) {
        super(repository, mapper, null);
    }
    @Override
    public void createDefaultUrgencyItems(User user) throws EntityExistsException, RollbackException {
        this.repository.saveAll(
                List.of(
                        new Urgency("Today", "#FF5252", 1, user),         // Red
                        new Urgency("This week", "#FFA726", 2, user),      // Orange
                        new Urgency("This month", "#FFD600", 3, user),     // Yellow
                        new Urgency("This year", "#4CAF50", 4, user)       // Green
                )
        );
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
}
