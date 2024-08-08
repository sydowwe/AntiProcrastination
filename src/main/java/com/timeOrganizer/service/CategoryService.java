package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.CategoryMapper;
import com.timeOrganizer.model.dto.request.extendable.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryService extends MyService<Category,ICategoryRepository,NameTextColorIconRequest, CategoryResponse,CategoryMapper>{
    @Autowired
    public CategoryService(ICategoryRepository repository, CategoryMapper mapper) {
        super(repository, mapper);
    }

    public List<CategoryResponse> getCategoriesByRoleId(long roleId)
    {
        return this.mapper.convertToFullResponseList(this.repository.findAllByActivities_Role_IdAndUserId(roleId, UserService.getLoggedUser().getId()));
    }
    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(NameTextColorIconRequest request)
    {
        return null;
    }
}
