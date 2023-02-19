package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IActivityRepository extends JpaRepository<Activity,Long> {
    List<Activity> findByRole(Role role);
    List<Activity> findByCategory(Category category);
}

