package com.timeOrganizer.model.entity;

import com.timeOrganizer.model.dto.request.ActivityRequest;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "activity", schema = "dbo")
public class Activity extends AbstractEntity {
    @Column(name = "is_on_to_do_list")
    private Boolean isOnToDoList;

    @Column(name = "is_necessary")
    @Nullable
    private Boolean isNecessary;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Activity(String name, String text, Boolean isOnToDoList, Boolean isNecessary, Role role, Category category) {
        super(name, text);
        this.isOnToDoList = isOnToDoList;
        this.isNecessary = isNecessary;
        this.role = role;
        this.category = category;
    }
}
