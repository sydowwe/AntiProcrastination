package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Activity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity,Long>, IMyRepository<Activity> {
    List<Activity> findByRoleIdAndUserId(long roleId, long userId);
    List<Activity> findByCategoryIdAndUserId(long categoryId, long userId);
    @Override
    List<Activity> findAllByUserId(long userId, Sort sort);
}

