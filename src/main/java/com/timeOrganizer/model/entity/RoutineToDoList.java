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
                @UniqueConstraint(name = "routineToDoList_unique_userId_name",columnNames = {"userId", "activityId"}),
        }, indexes = @Index(name = "idx_userId_timePeriodId",columnList = "userId,timePeriodId")
)
public class RoutineToDoList extends AbstractEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId",nullable = false)
    private Activity activity;
    @ManyToOne
    @JoinColumn(name = "timePeriodId", nullable = false)
    private RoutineTimePeriod timePeriod;
}