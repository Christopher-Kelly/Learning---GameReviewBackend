package com.example.reactlogin.loginObject.Game.DTO;


import com.example.reactlogin.loginObject.Rating.DTO.Rating;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum Category {
    ADULT(),
    ADVENTURE(),
    DATING(),
    GAMBLING(),
    KIDS(),
    PUZZLE(),
    SHOOTER(),
    SIM(),
    SOULS_LIKE(),
    SPORTS(),
    STORY(),
    THIRD_PERSON(),
    VIOLENT();

    private final String value;

    Category() {
        this.value = name().toLowerCase(Locale.ROOT);
    }

    public static Optional<Category> fromString(String input) {
        return Arrays.stream(Category.values())
                .filter(c -> c.value.equalsIgnoreCase(input))
                .findFirst();
    }

}
