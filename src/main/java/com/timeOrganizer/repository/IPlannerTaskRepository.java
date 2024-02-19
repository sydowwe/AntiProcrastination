package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.PlannerTask;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlannerTaskRepository extends IMyRepository<PlannerTask> {
}
