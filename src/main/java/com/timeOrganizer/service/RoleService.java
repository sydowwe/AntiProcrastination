package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.RoleMapper;
import com.timeOrganizer.model.dto.request.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService extends MyService<Role,IRoleRepository,RoleResponse,NameTextColorIconRequest,RoleMapper> implements IRoleService {
    @Autowired
    public RoleService(IRoleRepository repository, RoleMapper mapper, UserService userService) {
        super(repository, mapper, userService);
    }
    public List<RoleResponse> getRolesByCategory(long categoryId, long userId) {
        return this.mapper.convertToFullResponseList(this.repository.findByActivities_Category_IdAndUserId(categoryId,userId));
    }
}
