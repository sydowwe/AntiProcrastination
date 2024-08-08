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
@ToString
@Table(schema = "public", uniqueConstraints = {
    @UniqueConstraint(name = "urgency_unique_user_id_text", columnNames = {"user_id", "text"}),
    @UniqueConstraint(name = "urgency_unique_user_id_priority", columnNames = {"user_id", "priority"})
})
public class TaskUrgency extends AbstractEntity
{
    @Column(nullable = false)
    private String text;
    private String color;
    @Column(nullable = false)
    private int priority;
    @OneToMany(mappedBy = "taskUrgency")
    @ToString.Exclude
    private List<ToDoList> toDoListItems;

	public TaskUrgency(String text, String color, int priority, User user)
	{
		super(user);
		this.text = text;
		this.color = color;
		this.priority = priority;
	}
}