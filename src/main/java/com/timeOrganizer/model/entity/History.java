package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.MyIntTime;
import com.timeOrganizer.helper.MyIntTimeDBConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "history", schema = "test")
public class History extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "activityId",nullable = false)
    private Activity activity;
    private ZonedDateTime start;
    @Convert(converter = MyIntTimeDBConverter.class)
    private MyIntTime length;
    public int getLengthInSeconds() {
        return length.getHours() * 3600 + length.getMinutes() * 60 + length.getSeconds();
    }
}
