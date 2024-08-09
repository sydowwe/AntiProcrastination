package com.timeOrganizer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(schema = "public", uniqueConstraints = @UniqueConstraint(name = "planner_task_unique_user_id_star_timestamp", columnNames = {"user_id", "start_timestamp"}))
public class PlannerTask extends EntityWithActivity
{
    @Column(nullable = false)
    private Instant startTimestamp;
    @Column(nullable = false)
    private int minuteLength;
    private String color;
    private boolean isDone;
}
