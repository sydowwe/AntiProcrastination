package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IToDoListRepository extends  IMyRepository<ToDoList> {
    @Modifying
    @Query("UPDATE ToDoList t SET t.isDone = CAST(NOT (t.isDone) AS boolean) WHERE t.id IN :ids")
    int updateIsDoneByIds(@Param("ids") List<Long> ids) throws EntityNotFoundException;
}
