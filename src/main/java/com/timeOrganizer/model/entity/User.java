package com.timeOrganizer.model.entity;

import com.timeOrganizer.security.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user", schema = "test")
@ToString(exclude="scratchCodes")
public class User extends AbstractEntity{
    private String name;
    private String surname;
    //    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true)
    private String secretKey2FA;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ElementCollection
    private List<Integer> scratchCodes;
    @Column(nullable = false)
    private boolean isStayLoggedIn;
    @OneToMany(mappedBy = "user")
    private List<ToDoList> toDoLists;
    public boolean has2FA() {
        return this.secretKey2FA != null && !this.secretKey2FA.isBlank();
    }
}