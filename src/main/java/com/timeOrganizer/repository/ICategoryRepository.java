package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends IMyRepository<Category> {
}
