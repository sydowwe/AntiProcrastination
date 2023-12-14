package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "history", schema = "test")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;
    private ZonedDateTime start;
    private LocalTime length;
    public History(Activity activity, ZonedDateTime start, LocalTime length) {
        this.activity = activity;
        this.start = start;
        this.length = length;
    }
    public long getLengthInSeconds(){
        return length.getHour()*3600+length.getMinute()*60+length.getSecond();
    }
}
