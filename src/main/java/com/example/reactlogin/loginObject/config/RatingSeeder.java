package com.example.reactlogin.loginObject.config;

import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Game.Repository.gameDataRepository;
import com.example.reactlogin.loginObject.Rating.DTO.Rating;
import com.example.reactlogin.loginObject.Rating.Entity.ratingData;
import com.example.reactlogin.loginObject.Rating.Repository.ratingDataRepository;
import com.example.reactlogin.loginObject.User.Entity.userData;
import com.example.reactlogin.loginObject.User.Repository.userDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@Profile({"dev","test"})
public class RatingSeeder {

    @Autowired
    private gameDataRepository gameRepo;

    @Autowired
    private userDataRepository userRepo;

    @Autowired
    private ratingDataRepository ratingRepo;

    private final Random random = new Random();

    private Rating randomRating() {
        Rating[] values = Rating.values();
        return values[new Random().nextInt(values.length)];
    }

    private String randomReview() {
        String[] reviews = {
                "Great game.",
                "Really enjoyed it.",
                "Pretty fun overall.",
                "Not bad at all.",
                "Would recommend.",
                "Solid gameplay.",
                "Could be better.",
                "Had a good time playing.",
                "Decent experience.",
                "Loved the mechanics.",
                "Story was interesting.",
                "Gameplay felt smooth.",
                "A bit repetitive at times.",
                "Worth trying out.",
                "Enjoyable but not perfect."
        };

        return reviews[random.nextInt(reviews.length)];
    }

    @PostConstruct
    public void seedRatings() {
        try {

            if (ratingRepo.count() > 0) return;

            List<gameData> games = gameRepo.findAll();
            List<userData> users = userRepo.findAll();

            for (gameData game : games) {

                int reviewCount = 2 + random.nextInt(9); // 2 → 10 reviews

                Collections.shuffle(users);

                int created = 0;
                int index = 0;

                while (created < reviewCount && index < users.size()) {
                    userData user = users.get(index++);

                    if (ratingRepo.existsByUserAndGame(user, game)) continue;

                    ratingData rating = new ratingData();
                    rating.setGame(game);
                    rating.setUser(user);
                    rating.setRating(randomRating());
                    rating.setReview(randomReview());

                    System.out.println("Saving rating...");
                    ratingRepo.save(rating);
                    System.out.println("Saved rating id = " + rating.getId());
                    created++;
                }
            }
            ratingRepo.flush();
            System.out.println("Rating seeding done " + ratingRepo.count());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}