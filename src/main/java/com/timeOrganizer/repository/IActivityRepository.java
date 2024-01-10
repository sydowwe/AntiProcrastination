package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Activity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends IMyRepository<Activity> {
    List<Activity> findByRoleIdAndUserId(long roleId, long userId);
    List<Activity> findByCategoryIdAndUserId(long categoryId, long userId);
}

