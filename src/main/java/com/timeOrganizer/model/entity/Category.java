package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@Table(name = "category", schema = "test",
        uniqueConstraints = @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"})
)
public class Category extends NameTextEntity{
    @OneToMany(mappedBy = "category")
    private List<Activity> activities;
    private String color;
    private String icon;
}
