package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "task_urgency", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "priority"}))
public class Urgency extends AbstractEntity{
    @Column(nullable = false)
    private String text;
    private String color;
    @Column(nullable = false)
    private int priority;
/*    @Setter
    private String icon;*/
    @OneToMany(mappedBy = "urgency")
    private List<ToDoList> toDoListItems;

    public Urgency(String text, String color, int priority, User user) {
        super(user);
        this.text = text;
        this.color = color;
        this.priority = priority;
    }
}