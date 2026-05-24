package com.example.reactlogin.loginObject.Game.Repository;

import com.example.reactlogin.loginObject.Game.DTO.Category;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface gameDataRepository extends JpaRepository<gameData,Long> {
    Optional<gameData> findByName(String name);

    @Query("SELECT g FROM gameData g JOIN g.categories c WHERE c = :category")
    Page<gameData> findByCategory(
            @Param("category") Category category,
            Pageable pageable
    );
}
