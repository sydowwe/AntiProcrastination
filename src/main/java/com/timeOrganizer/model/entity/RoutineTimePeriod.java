package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"toDoListItems"})
@Table(schema = "public", uniqueConstraints = @UniqueConstraint(name = "routine_time_period_unique_user_id_text", columnNames = {"user_id", "text"}))
public class RoutineTimePeriod extends AbstractEntity {
    @Column(nullable = false)
    private String text;
    private String color;
    private int lengthInDays;
    private boolean isHiddenInView;
    @OneToMany(mappedBy = "timePeriod")
    private List<RoutineToDoList> toDoListItems;

	public RoutineTimePeriod(String text, String color, int lengthInDays, boolean isHiddenInView, User user)
	{
		super(user);
		this.color = color;
		this.isHiddenInView = isHiddenInView;
		this.lengthInDays = lengthInDays;
		this.text = text;
	}
}
