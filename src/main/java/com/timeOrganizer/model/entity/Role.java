package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(exclude = {"activities"})
@Entity
@Table(name = "role", schema = "public", uniqueConstraints = @UniqueConstraint(name = "role_unique_userId_name",columnNames = {"userId", "name"}))
public class Role extends NameTextColorEntity{
    @OneToMany(mappedBy = "role")
    private List<Activity> activities;
    private String icon;
}
