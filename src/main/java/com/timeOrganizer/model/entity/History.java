package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "history", schema = "dbo")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String activityName;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
    @Column(name = "time_of_start")
    private Timestamp timeOfStart;
    @Column(name = "length")
    private Time length;
    public History(String activityName, Activity activity, Timestamp timeOfStart, Time length) {
        this.activityName = activityName;
        this.activity = activity;
        this.timeOfStart = timeOfStart;
        this.length = length;
    }
}
