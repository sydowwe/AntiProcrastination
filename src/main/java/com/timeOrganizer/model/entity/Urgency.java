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
@ToString
@Entity
@Table(name = "task_urgency", schema = "public", uniqueConstraints = @UniqueConstraint(name = "urgency_unique_userId_name",columnNames = {"userId", "priority"}))
public class Urgency extends AbstractEntity{
    @Column(nullable = false)
    private String text;
    private String color;
    @Column(nullable = false)
    private int priority;
    @OneToMany(mappedBy = "urgency")
    @ToString.Exclude
    private List<ToDoList> toDoListItems;

    public Urgency(String text, String color, int priority, User user) {
        super(user);
        this.text = text;
        this.color = color;
        this.priority = priority;
    }
}