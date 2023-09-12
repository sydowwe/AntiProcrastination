package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "category", schema = "test")
public class Category extends AbstractEntity{
    @OneToMany(mappedBy = "category")
    private List<Activity> activities;
    private String color;
    private String icon;

    public Category(String name, String text, String color, String icon) {
        super(name, text);
        this.color = color;
        this.icon = icon;
    }
}
