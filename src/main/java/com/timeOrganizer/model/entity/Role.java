package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "role", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}))
public class Role extends NameTextEntity{
    @OneToMany(mappedBy = "role")
    private List<Activity> activities;
    private String color;
    private String icon;
}
