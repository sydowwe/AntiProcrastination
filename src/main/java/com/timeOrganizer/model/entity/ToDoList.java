package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@Table(name = "to_do_list", schema = "test",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_userId_name",columnNames = {"userId", "name"}),
    }, indexes = @Index(name = "idx_userId_urgencyId",columnList = "userId,urgencyId")
)
public class ToDoList extends NameTextEntity {
    @Column(nullable = false)
    private boolean isDone;
    @ManyToOne
    @JoinColumn(name = "urgencyId", nullable = false)
    private Urgency urgency;
}
