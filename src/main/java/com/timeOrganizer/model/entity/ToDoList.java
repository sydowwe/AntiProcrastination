package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(schema = "public",
        uniqueConstraints = {
            @UniqueConstraint(name = "to_do_list_unique_user_id_activity_id", columnNames = {"user_id", "activity_id"}),
        }, indexes = @Index(name = "idx_user_id_urgency_id", columnList = "user_id, urgency_id")
)
public class ToDoList extends AbstractEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    private Activity activity;
    @ManyToOne
    @JoinColumn(name = "task_urgency_id", nullable = false)
    private TaskUrgency taskUrgency;
}
