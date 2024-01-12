package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Urgency;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUrgencyRepository extends IMyRepository<Urgency> {
    @Override
    List<Urgency> findAllByUserId(long userId, Sort sort);
}