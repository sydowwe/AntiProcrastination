package com.timeOrganizer.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class NameTextEntity extends AbstractEntity{
    @Column(nullable = false)
    private String name;
    private String text;

    public NameTextEntity(String name, String text, User user)
    {
        super(user);
        this.name = name;
        this.text = text;
    }
}
