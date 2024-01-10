package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "activity", schema = "test")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class Activity extends NameTextEntity {
    private boolean isOnToDoList;
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
