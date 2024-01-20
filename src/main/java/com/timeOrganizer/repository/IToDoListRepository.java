package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
import com.timeOrganizer.model.groupedData.RoutineToDoListGroupedByTimePeriod;
import com.timeOrganizer.model.groupedData.ToDoListGroupedByUrgency;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IToDoListRepository extends  IMyRepository<ToDoList> {
    @Override
    List<ToDoList> findAllByUserId(long userId, Sort sort);

    @Query("SELECT t.urgency.color as urgencyColor, " +
            "       COLLECT(t) as toDoListItems " +
            "FROM ToDoList t " +
            "WHERE t.user.id = :userId " +
            "GROUP BY t.urgency.id")
    List<ToDoListGroupedByUrgency> getAllByUserIdGroupedByTimePeriod(@Param("userId") long userId);
    @Modifying
    @Query("UPDATE ToDoList t SET t.isDone = :isDone WHERE t.id = :id")
    void updateDoneById(@Param("id") long id,@Param("isDone") boolean isDone) throws EntityNotFoundException;
}
