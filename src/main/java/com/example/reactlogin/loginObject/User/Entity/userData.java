package com.example.reactlogin.loginObject.User.Entity;

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
@RequiredArgsConstructor
public class userData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "id")
    private Long id;

    private String username;
    private String password;
    private int phoneNumber;
    private String bio;

    private Roles role;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        userData userData = (userData) o;
        return getId() != null && Objects.equals(getId(), userData.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
