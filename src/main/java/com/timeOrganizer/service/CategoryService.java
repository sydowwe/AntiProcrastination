package com.timeOrganizer.service;

import com.timeOrganizer.exception.CategoryNotFoundException;
import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.repository.IActivityRepository;
import com.timeOrganizer.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;
    private final IActivityRepository activityRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository, IActivityRepository activityRepository) {
        this.categoryRepository = categoryRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public Category getCategoryById(@NotNull Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    @Override
    public Category createCategory(String name, String text) {
        return categoryRepository.save(new Category(name,text));
    }
    @Override
    public void deleteCategory(@NotNull Long id) {
        categoryRepository.deleteById(id);
    }
    @Override
    public Category updateCategory(Long id,String newName) {
        Category category = this.getCategoryById(id);
        category.setText(newName);
        return categoryRepository.save(category);
    }
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Activity> getActivitiesByCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new CategoryNotFoundException(id);
        }
        return activityRepository.findByCategory(category.get());
    }
}
