package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.PlannerTask;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IPlannerTaskRepository extends IEntityWithActivityRepository<PlannerTask>
{
	@Query("SELECT t FROM PlannerTask t WHERE t.user.id = :userId AND t.startTimestamp >= :startPoint AND t.startTimestamp <= :endPoint")
	List<PlannerTask> getAllByDateAndHourSpan(@Param("userId") long userId,@Param("startPoint") Instant filterStartPoint,@Param("endPoint") Instant filterEndPoint);

	@Modifying
	@Query("UPDATE PlannerTask p SET p.isDone = CAST(NOT (p.isDone) AS boolean) WHERE p.id IN :ids")
	int updateIsDoneByIds(@Param("ids") List<Long> ids) throws EntityNotFoundException;
}
