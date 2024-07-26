package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Alarm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAlarmRepository extends IMyRepository<Alarm>
{
//	@Query("SELECT t FROM PlannerTask t WHERE t.user.id = :userId AND t.startTimestamp >= :startPoint AND t.startTimestamp <= :endPoint")
//	List<PlannerTask> getAllByDateAndHourSpan(@Param("userId") long userId,@Param("startPoint") Instant filterStartPoint,@Param("endPoint") Instant filterEndPoint);

	@Modifying
	@Query("UPDATE Alarm a SET a.isActive = CAST(NOT (a.isActive) AS boolean) WHERE a.id IN :ids")
	int updateIsActiveByIds(@Param("ids") List<Long> ids) throws EntityNotFoundException;
}
