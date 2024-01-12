package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.AbstractEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IMyRepository<ENTITY extends AbstractEntity> extends JpaRepository<ENTITY, Long> {
    List<ENTITY> findAllByUserId(long userId, Sort sort);
}
