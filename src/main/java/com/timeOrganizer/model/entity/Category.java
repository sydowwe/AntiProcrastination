package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category", schema = "dbo")
public class Category extends AbstractEntity{
    @OneToMany(mappedBy = "category")
    private List<Activity> activities;
    public Category(final String name,final String text) {
        super(name,text);
    }
}
