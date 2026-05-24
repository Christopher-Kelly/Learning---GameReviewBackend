//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.reactlogin.loginObject.Rating.Controller;

import com.example.reactlogin.loginObject.Rating.DTO.ratingDataDTO;
import com.example.reactlogin.loginObject.Rating.DTO.ratingDataResponseFrontendDTO;
import com.example.reactlogin.loginObject.Rating.Entity.ratingData;
import com.example.reactlogin.loginObject.Rating.Service.ratingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/ratings"})
public class ratingController {
    @Autowired
    public ratingService ratingService;


    @GetMapping(
            value = {"/all"},
            produces = {"application/json"}
    )
    public ResponseEntity<List<ratingDataDTO>> getReviews(Pageable pageable) {
        return new ResponseEntity<>(this.ratingService.getAllRatings(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping(
            value = {"/rating/game"},
            produces = {"application/json"}
    )
    public ResponseEntity<Object> getReviewsForGame(@RequestParam Long id, Pageable pageable) {
        List<ratingDataDTO> ratings = this.ratingService.getRatingsByID(id);
        System.out.println("inside get reviews "+ id + ratings);
        if (!ratings.isEmpty()) {
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        }else{
            return new ResponseEntity<Object>(this.ratingService.getAllRatings(pageable).getContent(), HttpStatus.OK);
        }
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/rating/new")
    public ResponseEntity<?> postRating(@Valid @RequestBody ratingDataResponseFrontendDTO request) {
        System.out.println(request+ "posting rating");
        int success = ratingService.saveReview(request.getRating().getValue(), request.getGameId(), request.getUsername());


        return success == 0
                ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error with saving review");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ValidationError) {
        Map<String, String> errors = new HashMap<>();
        ValidationError.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
