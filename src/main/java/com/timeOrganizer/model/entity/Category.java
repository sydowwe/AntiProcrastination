package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"activities"})
@Table(schema = "public",
	uniqueConstraints = @UniqueConstraint(name = "category_unique_user_id_name", columnNames = {"user_id", "name"})
)
public class Category extends NameTextColorEntity{
    @OneToMany(mappedBy = "category")
    private List<Activity> activities;
    private String icon;
}
