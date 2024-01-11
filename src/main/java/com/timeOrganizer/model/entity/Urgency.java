package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@SuperBuilder
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
}