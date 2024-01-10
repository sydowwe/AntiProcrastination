package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.RoleMapper;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleService extends MyService<Role,IRoleRepository,RoleResponse,NameTextColorIconRequest,RoleMapper> implements IRoleService {
    private final ActivityService activityService;
    @Autowired
    public RoleService(IRoleRepository repository, RoleMapper mapper, UserService userService, ActivityService activityService) {
        super(repository, mapper, userService);
        this.activityService = activityService;
    }
    public List<RoleResponse> getRolesByCategory(long categoryId, long userId) {
        List<RoleResponse> roles = new ArrayList<>();
        activityService.getActivitiesByCategoryId(categoryId, userId).forEach(activity -> {
            if(!roles.contains(activity.getRole())){
                roles.add(activity.getRole());
            }
        });
        return roles;
    }
}
