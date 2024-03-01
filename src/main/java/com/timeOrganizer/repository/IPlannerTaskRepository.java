package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.PlannerTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface IPlannerTaskRepository extends IMyRepository<PlannerTask> {
	@Query("SELECT t FROM PlannerTask t WHERE t.user.id = :userId AND t.startTimestamp >= :startPoint AND t.startTimestamp <= :endPoint")
	List<PlannerTask> getAllByDateAndHourSpan(@Param("userId") long userId,@Param("startPoint") Instant filterStartPoint,@Param("endPoint") Instant filterEndPoint);
}
