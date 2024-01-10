package com.timeOrganizer.model.dto.mappers;

import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.model.entity.User;
import org.springframework.stereotype.Component;

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
    public Category createEntityFromRequest(NameTextColorIconRequest request, User user) {
        return Category.builder()
                .name(request.getName())
                .text(request.getText())
                .color(request.getColor())
                .icon(request.getIcon())
                .user(user)
                .build();
    }
    @Override
    public Category updateEntityFromRequest(Category entity, NameTextColorIconRequest request) {
        entity.setName(request.getName());
        entity.setText(request.getText());
        entity.setColor(request.getColor());
        entity.setIcon(request.getIcon());
        return entity;
    }
}