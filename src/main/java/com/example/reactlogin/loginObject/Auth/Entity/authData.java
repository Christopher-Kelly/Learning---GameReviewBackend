package com.example.reactlogin.loginObject.Auth.Entity;

import com.example.reactlogin.loginObject.config.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * id: a unique identifier for the user.
 * fullName: the user’s full name.
 * username: the user’s username.
 * password: the user’s password (stored as a hashed value).
 * phoneNumber: the user’s phone number.
 * birthday: the user’s date of birth.
 * bio: a short bio about the user.
 */

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class authData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id")
    private Long id;

    private String username;
    private String password;
    private int phoneNumber;
    private String bio;

    private Roles role;

}
