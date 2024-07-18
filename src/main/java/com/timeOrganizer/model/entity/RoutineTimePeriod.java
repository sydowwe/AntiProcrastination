package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"toDoListItems"})
@Entity
@Table(name = "routine_time_period", schema = "public", uniqueConstraints = @UniqueConstraint(name = "routineTimePeriod_unique_userId_name", columnNames = {"userId", "text"}))
public class RoutineTimePeriod extends AbstractEntity {
    @Column(nullable = false)
    private String text;
    private String color;
    private int lengthInDays;
    private boolean isHiddenInView;
    @OneToMany(mappedBy = "timePeriod")
    private List<RoutineToDoList> toDoListItems;

    public RoutineTimePeriod(String text, String color, int lengthInDays, boolean isHiddenInView, User user) {
        super(user);
        this.text = text;
        this.color = color;
        this.lengthInDays = lengthInDays;
        this.isHiddenInView = isHiddenInView;
    }
}
