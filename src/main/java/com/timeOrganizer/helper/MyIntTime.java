package com.timeOrganizer.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyIntTime {
    private int hours;
    private int minutes;
    private int seconds;

    public MyIntTime (int seconds){
        this.hours = seconds / 3600;
        this.minutes = (seconds % 3600) / 60;
        this.seconds = seconds % 60;
    }
}