package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "to_do_list", schema = "test",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "activityId"}),
    }, indexes = @Index(name = "idx_userId_urgencyId",columnList = "userId,urgencyId")
)
public class ToDoList extends AbstractEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne(optional = false)
    @JoinColumn(name = "activityId",nullable = false)
    private Activity activity;
    @ManyToOne
    @JoinColumn(name = "urgencyId", nullable = false)
    private Urgency urgency;
}
