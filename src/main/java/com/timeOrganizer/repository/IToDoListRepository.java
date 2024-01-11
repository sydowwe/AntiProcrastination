package com.timeOrganizer.repository;

import com.timeOrganizer.model.entity.ToDoList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IToDoListRepository extends JpaRepository<ToDoList,Long>, IMyRepository<ToDoList> {
    List<ToDoList> findByUrgencyId(Long urgencyId);
    List<ToDoList> findAllByUserIdOrderByUrgencyPriorityAsc(long userId);
    @Override
    List<ToDoList> findAllByUserId(long userId, Sort sort);
}
