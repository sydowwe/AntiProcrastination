package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "activity", schema = "test")
public class Activity extends AbstractEntity {
    private boolean isOnToDoList;
    private boolean isUnavoidable;
    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;
    @OneToMany(mappedBy = "activity")
    private List<History> historyList;

    public Activity(final String name,final String text,final boolean isOnToDoList,final boolean isUnavoidable,final Role role,final Category category) {
        super(name, text);
        this.isUnavoidable = isUnavoidable;
        this.isOnToDoList = isOnToDoList;
        this.role = role;
        this.category = category;
    }
}
