package com.timeOrganizer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
public abstract class NameTextEntity extends AbstractEntity{
    @Column(nullable = false)
    private String name;
    private String text;
}
