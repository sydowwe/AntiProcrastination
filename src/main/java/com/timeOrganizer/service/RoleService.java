package com.timeOrganizer.service;

import com.timeOrganizer.model.dto.mappers.RoleMapper;
import com.timeOrganizer.model.dto.request.extendable.NameTextColorIconRequest;
import com.timeOrganizer.model.dto.response.RoleResponse;
import com.timeOrganizer.model.entity.AbstractEntity;
import com.timeOrganizer.model.entity.Role;
import com.timeOrganizer.model.entity.User;
import com.timeOrganizer.repository.IRoleRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.RollbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleService extends MyService<Role,IRoleRepository,NameTextColorIconRequest,RoleResponse,RoleMapper> {
    @Autowired
    public RoleService(IRoleRepository repository, RoleMapper mapper) {
        super(repository, mapper);
    }

    public List<RoleResponse> getRolesByCategory(long categoryId)
    {
        return this.mapper.convertToFullResponseList(this.repository.findByActivities_Category_IdAndUserId(categoryId, UserService.getLoggedUser().getId()));
    }

    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(NameTextColorIconRequest request)
    {
        return null;
    }

    public RoleResponse getRoleByName(String name) throws EntityNotFoundException
    {
        return mapper.convertToFullResponse(repository.findByNameAndUserId(name, UserService.getLoggedUser().getId()).orElseThrow(() -> new EntityNotFoundException("Role with name " + name + " not found")));
    }
    public void createDefaultItems(User user) throws EntityExistsException, RollbackException
    {
        this.repository.saveAll(
            List.of(
                new Role("Planner task", "Quickly created activities in task planner", "", "calendar-days", user),
                new Role("To-do list task", "Quickly created activities in to-do list", "", "list-check", user),
                new Role("Routine task", "Quickly created activities in routine to-do list", "", "recycle", user)
            )
        );
    }
}
