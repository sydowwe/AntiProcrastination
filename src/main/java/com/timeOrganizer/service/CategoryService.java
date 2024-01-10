package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.CategoryMapper;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.CategoryResponse;
import com.timeOrganizer.model.entity.Category;
import com.timeOrganizer.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryService extends MyService<Category,ICategoryRepository, CategoryResponse,NameTextColorIconRequest,CategoryMapper> implements ICategoryService{
    private final ActivityService activityService;
    @Autowired
    public CategoryService(ICategoryRepository repository, CategoryMapper mapper, UserService userService, ActivityService activityService) {
        super(repository, mapper, userService);
        this.activityService = activityService;
    }
    public List<CategoryResponse> getCategoriesByRole(long roleId, long userId){
        List<CategoryResponse> categories = new ArrayList<>();
        activityService.getActivitiesByRoleId(roleId,userId).forEach(activity -> {
            if(!categories.contains(activity.getCategory())){
                categories.add(activity.getCategory());
            }
        });
        return categories;
    }
}
