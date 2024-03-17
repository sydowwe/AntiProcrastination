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
    public List<RoleResponse> getRolesByCategory(long categoryId, long userId) {
        return this.mapper.convertToFullResponseList(this.repository.findByActivities_Category_IdAndUserId(categoryId,userId));
    }

    @Override
    protected Map<String, ? extends AbstractEntity> getDependencies(NameTextColorIconRequest request) {
        return null;
    }

    public long getRoleByView(String viewName, long userId) throws EntityNotFoundException
    {
        return repository.findByNameAndUserId(viewName.replace(" ", ""),userId).orElseThrow(()->new EntityNotFoundException("Role with name "+ viewName+" not found")).getId();
    }
    public void createDefaultItems(User user) throws EntityExistsException, RollbackException
    {
        this.repository.saveAll(
            List.of(
                Role.builder().name("Planner task").text("Quickly created activities in task planner").user(user).color("").icon("calendar-days").build(),
                Role.builder().name("To-do list task").text("Quickly created activities in to-do list").user(user).color("").icon("list-check").build(),
                Role.builder().name("Routine task").text("Quickly created activities in routine to-do list").user(user).color("").icon("recycle").build()
            )
        );
    }
}
