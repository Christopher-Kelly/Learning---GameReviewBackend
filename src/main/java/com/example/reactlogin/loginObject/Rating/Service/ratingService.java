package com.example.reactlogin.loginObject.Rating.Service;


import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Game.Repository.gameDataRepository;
import com.example.reactlogin.loginObject.Rating.DTO.Rating;
import com.example.reactlogin.loginObject.Rating.DTO.ratingDataDTO;
import com.example.reactlogin.loginObject.Rating.Entity.ratingData;
import com.example.reactlogin.loginObject.Rating.Repository.ratingDataRepository;
import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.User.Entity.userData;
import com.example.reactlogin.loginObject.User.Repository.userDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ratingService {
    // no saving to  rating repo, instead save rating to user once authenticated and update game score

    @Autowired
    public userDataRepository userDataRepository;

    @Autowired
    public ratingDataRepository ratingDataRepository;

    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    private gameDataRepository gameDataRepository;

    public Page<ratingDataDTO> getAllRatings(Pageable pageable){
        Pageable sortedByUploadDateDesc =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("id").descending());

        return ratingDataRepository.findAll(sortedByUploadDateDesc)
                .map(ratingData -> modelMapper.map(ratingData, ratingDataDTO.class));
    }

    public Optional<List<ratingData>> getRatingsByGameName(String gameName) {
        return ratingDataRepository.findByGame_Name(gameName);
    }

    public List<ratingDataDTO> getRatingsByID(Long gameId) {
        return ratingDataRepository.findByGame_Id(gameId).stream().map((element) -> modelMapper.map(element, ratingDataDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public int saveReview(double ratingValue, long id, String username) {
        // 1️⃣ fetch user
        Optional<userData> user = userDataRepository.findByUsername(username);

        Optional<gameData> gameFound = gameDataRepository.findById(id);

        if (user.isEmpty()){
            System.out.println("User not found");
            return -1;
        }

        if (gameFound.isEmpty()){
            System.out.println("Game not found");
            return -2;
        }

        System.out.println(
                "game found in saveReview " + gameFound.get().toString()
        );

        // 2️⃣ check for existing rating
        Optional<ratingData> existing =
                ratingDataRepository.findByUserAndGame(
                        user.get(),
                        gameFound.get()
                );
        
        if (existing.isPresent()) {
            // update existing rating
            existing.get().setRating(Rating.from(ratingValue));
            ratingDataRepository.save(existing.get());
            return 0;
        }

        // 3️⃣ create new rating
        ratingData newRating = new ratingData();
        newRating.setUser(modelMapper.map(user, userData.class));
        newRating.setGame(modelMapper.map(gameFound.get(), gameData.class));
        newRating.setRating(Rating.from(ratingValue));
        ratingDataRepository.save(newRating);

        // update game average rating
        double oldRating = gameFound.get().getAverageRating() * gameFound.get().getReviewerNumber();
        int reviewNumber = gameFound.get().getReviewerNumber()  + 1 ;
        double newGameRating = (newRating.getRating().getValue() + oldRating) / reviewNumber;

        gameFound.get().setAverageRating(newGameRating);
        gameFound.get().setReviewerNumber(reviewNumber);
        gameDataRepository.save(gameFound.get());
        System.out.println(
                "game to be saved " + gameFound.get().toString()
        );
        return 0;
    }

}
