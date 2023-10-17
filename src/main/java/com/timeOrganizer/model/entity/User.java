package com.timeOrganizer.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user", schema = "test")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String password;
    @Nullable
    private String secretKey; // Store the secret key for Google Authenticator
}