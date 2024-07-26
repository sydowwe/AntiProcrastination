package com.timeOrganizer.repository;

import com.timeOrganizer.exception.UserNotFoundException;
import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Optional;

@Repository
//@Transactional(rollbackFor = {SQLException.class, DataAccessException.class})
@Transactional
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.email = :email")
    Integer updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);

    @Modifying
    @Query("UPDATE User u SET u.currentLocale = :currentLocale WHERE u.id = :id")
    Integer updateCurrentLocaleById(@Param("id") long id, @Param("currentLocale") AvailableLocales currentLocale);
    @Modifying
    @Query("UPDATE User u SET u.isStayLoggedIn = :isStayLoggedIn WHERE u.id = :id")
    void updateStayLoggedInById(@Param("id") long id,@Param("isStayLoggedIn") boolean isStayLoggedIn) throws UserNotFoundException;

    @Modifying
    @Query("UPDATE User u SET u.timezone = :timezone WHERE u.id = :userId")
    void updateUserTimezone(@Param("userId") Long userId, @Param("timezone") ZoneId timezone);
}
