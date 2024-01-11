package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long>, IMyRepository<Category> {
    List<Category> findAllByActivities_Role_IdAndUserId(long roleId, long userId);
    @Override
    List<Category> findAllByUserId(long userId, Sort sort);
}
