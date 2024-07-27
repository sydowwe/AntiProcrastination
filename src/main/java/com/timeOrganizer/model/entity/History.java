package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.helper.MyIntTimeDBConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "history", schema = "public")
//TODO MAYBE ADD indexing
public class History extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "activityId",nullable = false)
    private Activity activity;
    @Column(nullable = false)
    private Instant start;
    @Convert(converter = MyIntTimeDBConverter.class)
    @Column(nullable = false)
    private MyIntTime length;
    @Column(nullable = false, name = "end_time")
    private Instant endTime = start.plusSeconds(length.getInSeconds());
}
