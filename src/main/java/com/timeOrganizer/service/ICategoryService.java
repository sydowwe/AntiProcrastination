package com.timeOrganizer.service;

import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category createCategory(String name,String text);
    void deleteCategory(Long id);
    Category updateCategory(Long id, String newName);
    List<Activity> getActivitiesByCategory(Long id);
}
