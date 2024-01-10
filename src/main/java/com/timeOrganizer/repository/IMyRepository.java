package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.AbstractEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMyRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY,Long> {
    List<ENTITY> findAllByUserId(long userId, Sort sort);
}
