package com.timeOrganizer.model.groupedData;

import com.timeOrganizer.model.entity.RoutineToDoList;
import com.timeOrganizer.model.entity.ToDoList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class ToDoListGroupedByUrgency {
    private String urgencyColor;
    private List<ToDoList> toDoListItems;
}
