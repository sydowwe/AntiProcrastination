package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
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
