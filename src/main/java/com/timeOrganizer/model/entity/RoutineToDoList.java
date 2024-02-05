package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "routine_to_do_list", schema = "test",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}),
        }, indexes = @Index(name = "idx_userId_timePeriodId",columnList = "userId,timePeriodId")
)
public class RoutineToDoList extends NameTextEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne
    @JoinColumn(name = "timePeriodId", nullable = false)
    private RoutineTimePeriod timePeriod;
}