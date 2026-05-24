package com.example.reactlogin.loginObject.Rating.Entity;

import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Rating.DTO.Rating;
import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.User.Entity.userData;
import com.example.reactlogin.loginObject.config.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@RequiredArgsConstructor
@Table(
        name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_id"})
)
public class ratingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private userData user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private gameData game;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    private String review;

}
