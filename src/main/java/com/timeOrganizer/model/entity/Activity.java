package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"historyList"})
@Entity
@Table(name = "activity", schema = "test", uniqueConstraints = @UniqueConstraint(name = "unique_userId_name", columnNames = {"userId", "name"}))
public class Activity extends NameTextEntity {
    @Column(nullable = false)
    private boolean isOnToDoList;
    @Column(nullable = false)
    private boolean isUnavoidable;
    //TODO ADD cascade types
    @ManyToOne(optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
    @OneToMany(mappedBy = "activity")
    private List<History> historyList;
}
