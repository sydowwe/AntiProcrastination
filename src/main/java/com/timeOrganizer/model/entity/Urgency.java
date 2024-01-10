package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "task_urgency", schema = "test")
public class Urgency extends AbstractEntity{
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    private String text;
    private String color;
    private int priority;
/*    @Setter
    private String icon;*/
    @OneToMany(mappedBy = "urgency")
    private List<ToDoList> toDoListItems;
}