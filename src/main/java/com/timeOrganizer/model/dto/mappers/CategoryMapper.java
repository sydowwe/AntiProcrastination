package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Category;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CategoryMapper extends AbstractInOutMapper<Category,CategoryResponse, NameTextColorIconRequest>{
    @Override
    public CategoryResponse convertToFullResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .text(category.getText())
                .color(category.getColor())
                .icon(category.getIcon())
                .build();
    }
    @Override
    public Category updateEntityFromRequest(Category entity, NameTextColorIconRequest request, Map<String, ? extends AbstractEntity> dependencies) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        entity.setIcon(request.getIcon());
        return entity;
    }

    @Override
    Category createEmptyEntity() {
        return new Category();
    }
}