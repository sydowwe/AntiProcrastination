package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHistoryRepository extends JpaRepository<History,Long>, IMyRepository<History>, JpaSpecificationExecutor<History> {
    @Override
    List<History> findAllByUserId(long userId, Sort sort);

}
