package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Activity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IActivityRepository extends IMyRepository<Activity>, JpaSpecificationExecutor<Activity>
{
}

