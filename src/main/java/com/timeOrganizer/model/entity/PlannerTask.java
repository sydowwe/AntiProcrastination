package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "plannerTask", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_startTimestamp",columnNames = {"userId", "startTimestamp"}))
public class PlannerTask extends NameTextColorEntity{
    @Column(nullable = false)
    private Instant startTimestamp;
    @Column(nullable = false)
    private int minuteLength;
    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;
}
