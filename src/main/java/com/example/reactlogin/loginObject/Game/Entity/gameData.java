package com.example.reactlogin.loginObject.Game.Entity;

import com.example.reactlogin.loginObject.Game.DTO.Category;
import com.example.reactlogin.loginObject.Rating.DTO.Rating;
import com.example.reactlogin.loginObject.config.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
public class gameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id")
    private Long id;

    private String name;
    private double averageRating;
    private URL picURL;
    private String bio;
    private int reviewerNumber;

    @ElementCollection(targetClass = Category.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "game_categories", joinColumns = @JoinColumn(name = "game_id"))
    @Column(name = "category")
    private List<Category> categories;

    private LocalDateTime timeAdded;

}
