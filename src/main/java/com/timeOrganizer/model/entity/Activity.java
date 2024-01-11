package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@Entity
@Table(name = "activity", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name", columnNames = {"userId", "name"}))
public class Activity extends NameTextEntity {
    @Column(nullable = false)
    private boolean isOnToDoList;
    @Column(nullable = false)
    private boolean isUnavoidable;
    @ManyToOne
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "activity")
    private List<History> historyList;
}
