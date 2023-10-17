package com.timeOrganizer.service;

import com.timeOrganizer.exception.TaskUrgencyNotFoundException;
import com.timeOrganizer.model.entity.Urgency;
import com.timeOrganizer.repository.IUrgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UrgencyService implements IUrgencyService {
    private final IUrgencyRepository urgencyRepository;

    @Autowired
    public UrgencyService(IUrgencyRepository urgencyRepository) {
        this.urgencyRepository = urgencyRepository;
    }
    @Override
    public Urgency updateUrgencyItem(Long id, String color, String icon) {
        Urgency urgency = this.getUrgencyItemById(id);
        urgency.setColor(color);
        urgency.setIcon(icon);
        return urgencyRepository.save(urgency);
    }
    @Override
    public Urgency getUrgencyItemById(Long id) {
        return urgencyRepository.findById(id)
                .orElseThrow(() -> new TaskUrgencyNotFoundException(id));
    }
    @Override
    public List<Urgency> getAllUrgencyItems() {
        return urgencyRepository.findAll();
    }
}
