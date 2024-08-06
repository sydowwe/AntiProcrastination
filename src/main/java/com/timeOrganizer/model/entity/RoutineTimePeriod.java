package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@SuperBuilder
@ToString(exclude = {"toDoListItems"})
@AllArgsConstructor
@Table(schema = "public", uniqueConstraints = @UniqueConstraint(name = "routine_time_period_unique_user_id_text", columnNames = {"user_id", "text"}))
public class RoutineTimePeriod extends AbstractEntity {
    @Column(nullable = false)
    private String text;
    private String color;
    private int lengthInDays;
    private boolean isHiddenInView;
    @OneToMany(mappedBy = "timePeriod")
    private List<RoutineToDoList> toDoListItems;
}
