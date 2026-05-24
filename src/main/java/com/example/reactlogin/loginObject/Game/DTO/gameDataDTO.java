package com.example.reactlogin.loginObject.Game.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class gameDataDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Rating is mandatory")
    private double averageRating;

    @NotEmpty(message = "At least one category is required")
    private List<Category> categories;

    @NotNull(message = "Time added is mandatory")
    private LocalDateTime timeAdded;

    private URL picURL;
    private String bio;
    private int reviewerNumber;
}
