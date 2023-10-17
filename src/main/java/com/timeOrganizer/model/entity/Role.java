package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Entity
@Table(name = "role", schema = "test")
public class Role extends AbstractEntity{
    @OneToMany(mappedBy = "role")
    private List<Activity> activities;
    private String color;
    private String icon;

    public Role(String name, String text, String color, String icon) {
        super(name, text);
        this.color = color;
        this.icon = icon;
    }
}
