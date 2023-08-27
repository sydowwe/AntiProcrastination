package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Column(name = "start")
    private LocalDateTime start;
    @Column(name = "length")
    private LocalTime length;
    public History(String activityName, Activity activity, LocalDateTime start, LocalTime length) {
        this.activityName = activityName;
        this.activity = activity;
        this.start = start;
        this.length = length;
    }
}
