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
@Entity
@ToString(exclude = {"historyList", "webExtensionDataList"})
@Table(schema = "public", uniqueConstraints = @UniqueConstraint(name = "activity_unique_user_id_name", columnNames = {"user_id", "name"}))
public class Activity extends NameTextEntity
{
    @Column(nullable = false)
    private boolean isOnToDoList;
    @Column(nullable = false)
    private boolean isUnavoidable;
    //TODO ADD cascade types
    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
	@ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "activity")
    private List<History> historyList;
    @OneToMany(mappedBy = "activity")
    private List<WebExtensionData> webExtensionDataList;
}
