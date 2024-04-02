package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.RoutineTimePeriod;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoutineToDoListTimePeriodRepository extends IMyRepository<RoutineTimePeriod>{
	@Modifying
	@Query("UPDATE RoutineTimePeriod t SET t.isHiddenInView = CAST(NOT (t.isHiddenInView) AS boolean) WHERE t.id = :id")
	int updateIsHiddenInView(@Param("id") long id) throws EntityNotFoundException;
}
