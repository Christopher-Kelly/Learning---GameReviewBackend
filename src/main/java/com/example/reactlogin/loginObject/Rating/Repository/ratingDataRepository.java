package com.example.reactlogin.loginObject.Rating.Repository;

import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Rating.Entity.ratingData;
import com.example.reactlogin.loginObject.User.Entity.userData;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ratingDataRepository extends JpaRepository<ratingData,Long> {
    Optional<ratingData> findByUserUsernameAndGameName(String username, String gameName);

    Optional<ratingData> findByUserAndGame(userData user, gameData game);

    Optional<ratingData> findByUserAndGame(@NotBlank(message = "Username is mandatory") String username, @NotBlank(message = "Game is mandatory") String name);

    Optional<List<ratingData>> findByGame_Name(String gameName);

    List<ratingData> findByGame_Id(Long gameId);

    boolean existsByUserAndGame(userData user, gameData game);
}