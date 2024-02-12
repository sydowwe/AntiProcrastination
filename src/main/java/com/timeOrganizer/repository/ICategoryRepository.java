package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends IMyRepository<Category> {
    List<Category> findAllByActivities_Role_IdAndUserId(long roleId, long userId);
}
