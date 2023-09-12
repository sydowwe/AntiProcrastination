package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Urgency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUrgencyRepository extends JpaRepository<Urgency, Long> {
}