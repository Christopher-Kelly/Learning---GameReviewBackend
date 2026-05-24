package com.example.reactlogin.loginObject.Rating.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@Getter
public enum Rating {
    HALF_STAR(0.5),
    ONE_STAR(1.0),
    ONE_HALF_STARS(1.5),
    TWO_STARS(2.0),
    TWO_HALF_STARS(2.5),
    THREE_STARS(3.0),
    THREE_HALF_STARS(3.5),
    FOUR_STARS(4.0),
    FOUR_HALF_STARS(4.5),
    FIVE_STARS(5.0);

    private final double value;

    Rating(double value) {
        this.value = value;
    }

    @JsonCreator
    public static Rating from(Object input) {
        if (input instanceof Number number) {
            double value = number.doubleValue();
            for (Rating r : values()) {
                if (Double.compare(r.value, value) == 0) {
                    return r;
                }
            }
        }

        if (input instanceof String str) {
            try {
                return Rating.valueOf(str.toUpperCase());
            } catch (IllegalArgumentException ignored) {}
        }

        throw new IllegalArgumentException("Invalid rating value: " + input);
    }
}