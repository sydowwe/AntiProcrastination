package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"activities"})
@Entity
@Table(name = "category", schema = "test",
        uniqueConstraints = @UniqueConstraint(name = "category_unique_userId_name",columnNames = {"userId", "name"})
)
public class Category extends NameTextColorEntity{
    @OneToMany(mappedBy = "category")
    private List<Activity> activities;
    private String icon;
}
