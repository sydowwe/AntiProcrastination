package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
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

    @Modifying
    @Query("UPDATE ToDoList t SET t.isDone = :isDone WHERE t.id = :id")
    void updateDoneById(@Param("id") long id,@Param("isDone") boolean isDone) throws EntityNotFoundException;
}
