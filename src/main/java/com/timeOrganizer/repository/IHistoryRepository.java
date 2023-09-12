package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface IHistoryRepository extends JpaRepository<History,Long> {
    List<History> getHistoriesByStartBetween(ZonedDateTime start, ZonedDateTime end);
}
