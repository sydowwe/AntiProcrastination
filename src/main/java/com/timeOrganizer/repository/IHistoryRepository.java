package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface IHistoryRepository extends JpaRepository<History,Long> {
    List<History> getHistoriesByStartBetweenOrderByStart(ZonedDateTime start, ZonedDateTime end);
    List<History> getHistoriesByActivityId(Long activityId);
    List<History> getHistoriesByActivity_CategoryId(Long categoryId);
    List<History> getHistoriesByActivity_RoleId(Long roleId);
    List<History> getHistoriesByActivity_CategoryIdAndActivity_RoleId(Long categoryId,Long roleId);
}
