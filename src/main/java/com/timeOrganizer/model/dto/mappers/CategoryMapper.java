package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse convertToFullResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setText(category.getText());
        response.setColor(category.getColor());
        response.setIcon(category.getIcon());
        return response;
    }
}