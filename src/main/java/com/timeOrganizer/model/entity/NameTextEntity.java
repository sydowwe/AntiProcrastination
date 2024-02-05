package com.timeOrganizer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public abstract class NameTextEntity extends AbstractEntity{
    @Column(nullable = false)
    private String name;
    private String text;
}
