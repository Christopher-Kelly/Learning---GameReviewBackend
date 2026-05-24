package com.example.reactlogin.loginObject.Rating.DTO;

import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.config.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ratingDataResponseFrontendDTO {

    @NotNull(message = "Game id is required")
    private Long gameId;

    @NotNull(message = "Rating is required")
    private Rating rating;

    @NotBlank(message = "Username is required")
    private String username;

    @NotNull(message = "User ID is required")
    private Long userID;

    private String review;
}