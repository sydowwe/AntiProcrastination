package com.timeOrganizer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class NameTextEntity extends AbstractEntity{
    @Column(nullable = false)
    private String name;
    private String text;
}
