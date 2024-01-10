package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.UrgencyMapper;
import com.timeOrganizer.model.dto.request.UrgencyRequest;
import com.timeOrganizer.model.dto.response.UrgencyResponse;
import com.timeOrganizer.model.entity.Urgency;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IUrgencyRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UrgencyService extends MyService<Urgency,IUrgencyRepository, UrgencyResponse, UrgencyRequest,UrgencyMapper> implements IUrgencyService {
    @Autowired
    public UrgencyService(IUrgencyRepository repository, UrgencyMapper mapper) {
        super(repository, mapper, null);
    }
    @Override
    public void createDefaultUrgencyItems(User user) throws EntityExistsException, RollbackException {
        List<Urgency> defaultUrgencyItems = List.of(
                Urgency.builder()
                        .priority(1)
                        .text("Today")
                        .color("red")
                        .user(user).build(),
                Urgency.builder()
                        .priority(2)
                        .text("This week")
                        .color("orange")
                        .user(user).build(),
                Urgency.builder()
                        .priority(3)
                        .text("This month")
                        .color("yellow")
                        .user(user).build(),
                Urgency.builder()
                        .priority(4)
                        .text("This year")
                        .color("green")
                        .user(user).build());
        this.repository.saveAll(defaultUrgencyItems);
    }
}
