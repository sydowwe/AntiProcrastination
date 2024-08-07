package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.RoutineToDoList;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRoutineToDoListRepository extends IEntityWithActivityRepository<RoutineToDoList>
{
    @Modifying
    @Query("UPDATE RoutineToDoList t SET t.isDone = CAST(NOT (t.isDone) AS boolean) WHERE t.id IN :ids")
    void updateIsDoneByIds(@Param("ids") List<Long> ids) throws EntityNotFoundException;
/*    @Modifying
    @Query("DELETE FROM RoutineToDoList t WHERE t.id IN :ids")
    int batchDelete(@Param("ids") List<Long> ids);*/
}
