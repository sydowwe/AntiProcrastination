package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToDoListRepository extends IMyRepository<ToDoList> {
    List<ToDoList> findByUrgencyId(Long urgencyId);
    List<ToDoList> findAllByUserIdOrderByUrgencyPriorityAsc(long userId);
}
