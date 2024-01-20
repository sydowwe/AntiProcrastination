package com.timeOrganizer.model.groupedData;

import com.timeOrganizer.model.entity.RoutineToDoList;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class RoutineToDoListGroupedByTimePeriod {
    private String timePeriodName;
    private List<RoutineToDoList> routineToDoListItems;
}
