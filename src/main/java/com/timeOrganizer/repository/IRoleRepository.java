package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleRepository extends  IMyRepository<Role> {

    List<Role> findByActivities_Category_IdAndUserId(long categoryId, long userId);

}
