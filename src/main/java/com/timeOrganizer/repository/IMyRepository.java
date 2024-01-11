package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.AbstractEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IMyRepository<ENTITY extends AbstractEntity> {
    List<ENTITY> findAllByUserId(long userId, Sort sort);
    void deleteById(long id);
    Optional<ENTITY> findById(long id);
    ENTITY getReferenceById(Long aLong);
    <S extends ENTITY> S save(S entity);
}
