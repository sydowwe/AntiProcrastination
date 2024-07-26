package com.timeOrganizer.model.entity;

import com.timeOrganizer.helper.AvailableLocales;
import com.timeOrganizer.helper.ZoneIdDBConverter;
import com.timeOrganizer.security.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZoneId;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user", schema = "public")
@ToString(exclude = {"scratchCodes", "activityList", "categoryList", "historyList", "roleList", "toDoLists", "urgencyList"})
public class User implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
    private List<Urgency> urgencyList;
    public boolean has2FA() {
        return this.secretKey2FA != null && !this.secretKey2FA.isBlank();
    }
}