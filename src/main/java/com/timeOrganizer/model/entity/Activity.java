package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"historyList", "webExtensionDataList"})
@Entity
@Table(name = "activity", schema = "public", uniqueConstraints = @UniqueConstraint(name = "activity_unique_userId_name", columnNames = {"userId", "name"}))
public class Activity extends NameTextEntity {
    @Column(nullable = false)
    private boolean isOnToDoList;
    @Column(nullable = false)
    private boolean isUnavoidable;
    //TODO ADD cascade types
    @ManyToOne(optional = false)
    @JoinColumn(name = "roleId", nullable = false)
    private Role role;
    @ManyToOne()
    @JoinColumn(name = "categoryId")
    private Category category;
    @OneToMany(mappedBy = "activity")
    private List<History> historyList;
    @OneToMany(mappedBy = "activity")
    private List<WebExtensionData> webExtensionDataList;
}
