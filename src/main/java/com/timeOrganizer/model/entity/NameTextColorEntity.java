package com.timeOrganizer.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class NameTextColorEntity extends NameTextEntity{
    private String color;
}
