package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.helper.MyIntTimeDBConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "history", schema = "test")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;
    private ZonedDateTime start;
    @Convert(converter = MyIntTimeDBConverter.class)
    private MyIntTime length;

    public History(Activity activity, ZonedDateTime start, MyIntTime length) {
        this.activity = activity;
        this.start = start;
        this.length = length;
    }

    public int getLengthInSeconds() {
        return length.getHours() * 3600 + length.getMinutes() * 60 + length.getSeconds();
    }
}
