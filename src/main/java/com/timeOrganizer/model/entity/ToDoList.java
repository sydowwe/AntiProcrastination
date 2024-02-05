package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "to_do_list", schema = "test",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}),
    }, indexes = @Index(name = "idx_userId_urgencyId",columnList = "userId,urgencyId")
)
public class ToDoList extends NameTextEntity {
    @Column(nullable = false)
    private boolean isDone = false;
    @ManyToOne
    @JoinColumn(name = "urgencyId", nullable = false)
    private Urgency urgency;
}
