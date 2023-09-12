package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToDoListRepository extends JpaRepository<ToDoList,Long> {
    List<ToDoList> findByUrgencyId(Long urgencyId);
}
