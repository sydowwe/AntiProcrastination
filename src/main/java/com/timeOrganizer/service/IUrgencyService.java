package com.timeOrganizer.service;

import com.timeOrganizer.model.entity.Urgency;

import java.util.List;

public interface IUrgencyService {
    Urgency updateUrgencyItem(Long id, String color, String icon);
    Urgency getUrgencyItemById(Long id);
    List<Urgency> getAllUrgencyItems();
}
