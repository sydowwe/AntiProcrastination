package com.timeOrganizer.service;

import com.timeOrganizer.exception.CategoryNotFoundException;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.repository.IActivityRepository;
import com.timeOrganizer.repository.ICategoryRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(@NotNull Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    @Override
    public Category createCategory(NameTextColorIconRequest categoryRequest) {
        Category category = new Category(categoryRequest.getName(),categoryRequest.getText(),categoryRequest.getColor(),categoryRequest.getIcon());
        return categoryRepository.save(category);
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
}
