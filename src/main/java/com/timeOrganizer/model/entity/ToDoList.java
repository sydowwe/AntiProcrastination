package com.timeOrganizer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "to_do_list", schema = "test")
public class ToDoList extends AbstractEntity {
    private boolean isDone;
    @ManyToOne
    @JoinColumn(name = "urgencyId")
    private Urgency urgency;

    public ToDoList(String name, String text, boolean isDone, Urgency urgency) {
        super(name, text);
        this.isDone = isDone;
        this.urgency = urgency;
    }
}
