package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "time_of_start")
    private LocalTime timeOfStart;
    @Column(name = "length")
    private LocalTime length;
    public History(String activityName, Activity activity, LocalDate date, LocalTime timeOfStart, LocalTime length) {
        this.activityName = activityName;
        this.activity = activity;
        this.date = date;
        this.timeOfStart = timeOfStart;
        this.length = length;
    }
}
