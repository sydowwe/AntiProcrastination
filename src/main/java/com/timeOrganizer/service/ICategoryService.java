package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category createCategory(NameTextColorIconRequest categoryRequest);
    void deleteCategory(Long id);
    Category updateCategory(Long id, String newName);
}
