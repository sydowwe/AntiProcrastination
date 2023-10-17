package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.Activity;
import com.timeOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    User findById(long id);

}
