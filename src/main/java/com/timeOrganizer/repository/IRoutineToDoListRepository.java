package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.RoutineToDoList;
import com.timeOrganizer.model.groupedData.RoutineToDoListGroupedByTimePeriod;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRoutineToDoListRepository extends  IMyRepository<RoutineToDoList> {
    @Query("SELECT t.timePeriod.text as timePeriodName, " +
            "       COLLECT(t) as routineToDoListItems " +
            "FROM RoutineToDoList t " +
            "WHERE t.user.id = :userId " +
            "GROUP BY t.timePeriod.lengthInDays")
    List<RoutineToDoListGroupedByTimePeriod> getAllByUserIdGroupedByTimePeriod(@Param("userId") long userId);
    @Modifying
    @Query("UPDATE ToDoList t SET t.isDone = :isDone WHERE t.id = :id")
    void updateDoneById(@Param("id") long id, @Param("isDone") boolean isDone) throws EntityNotFoundException;
}
