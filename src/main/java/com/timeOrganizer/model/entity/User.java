package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.helper.ZoneIdDBConverter;
import com.timeOrganizer.security.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(schema = "public")
@ToString(exclude = {"scratchCodes", "activityList", "categoryList", "historyList", "roleList", "toDoLists", "taskUrgencyList"})
public class User implements IEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private long id;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private Instant createdTimestamp;
    @LastModifiedDate
    @Column(nullable = false)
    private Instant modifiedTimestamp;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    //    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String secretKey2FA;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    @ElementCollection
    private List<Integer> scratchCodes;
    @Column(nullable = false)
    private boolean isStayLoggedIn;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AvailableLocales currentLocale = AvailableLocales.SK;
    @Convert(converter = ZoneIdDBConverter.class)
    private ZoneId timezone;

    @OneToMany(mappedBy = "user")
    private List<Activity> activityList;
    @OneToMany(mappedBy = "user")
    private List<Category> categoryList;
    @OneToMany(mappedBy = "user")
    private List<History> historyList;
    @OneToMany(mappedBy = "user")
    private List<Role> roleList;
    @OneToMany(mappedBy = "user")
    private List<ToDoList> toDoLists;
    @OneToMany(mappedBy = "user")
    private List<TaskUrgency> taskUrgencyList;

    public boolean has2FA()
    {
        return this.secretKey2FA != null && !this.secretKey2FA.isBlank();
    }
}