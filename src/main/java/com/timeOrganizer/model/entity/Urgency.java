package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "task_urgency", schema = "test")
public class Urgency {
    @Id
    private long id;
    private String text;
    @Setter
    private String color;
/*    @Setter
    private String icon;*/
    @OneToMany(mappedBy = "urgency")
    private List<ToDoList> toDoListItems;
}