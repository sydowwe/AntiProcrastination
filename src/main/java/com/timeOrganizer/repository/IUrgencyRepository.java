package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.TaskUrgency;
import org.springframework.stereotype.Repository;

@Repository
public interface IUrgencyRepository extends IMyRepository<TaskUrgency>
{
}