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
@Table(name = "role", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}))
public class Role extends NameTextColorEntity{
    @OneToMany(mappedBy = "role")
    private List<Activity> activities;
    private String icon;
}
