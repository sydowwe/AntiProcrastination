package com.timeOrganizer.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class NameTextColorEntity extends NameTextEntity{
    private String color;
}
