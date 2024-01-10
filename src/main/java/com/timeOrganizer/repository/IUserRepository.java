package com.timeOrganizer.repository;

import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
//@Transactional(rollbackFor = {SQLException.class, DataAccessException.class})
@Transactional
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    //TODO NEEDS SANITIZING ???
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
    Integer updatePasswordByEmail(String email, String newPassword);
    @Modifying
    @Query("UPDATE User u SET u.isStayLoggedIn = :isStayLoggedIn WHERE u.id = :id")
    void updateStayLoggedInById(long id, boolean isStayLoggedIn) throws UserNotFoundException;
}
