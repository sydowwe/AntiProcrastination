package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "role", schema = "dbo")
public class Role extends AbstractEntity{
    @OneToMany(mappedBy = "role")
    private List<Activity> activities;
    public Role(final String name,final String text) {
        super(name,text);
    }
}
