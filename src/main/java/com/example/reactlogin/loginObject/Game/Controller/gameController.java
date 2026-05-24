//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.reactlogin.loginObject.Game.Controller;

import com.example.reactlogin.loginObject.Game.DTO.Category;
import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Game.Service.gameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@RestController
@RequestMapping({"/api/game"})
public class gameController {
    @Autowired
    public gameService gameService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(
            value = {"/all"},
            produces = {"application/json"}
    )
    public ResponseEntity<List<gameDataDTO>> getAllGames(Pageable pageable) {
        return new ResponseEntity<>(this.gameService.getAllGames(pageable, "id").getContent(), HttpStatus.OK);
    }

    @GetMapping(
            value = {"/categories/all"},
            produces = {"application/json"}
    )
    public ResponseEntity<Category[]> getAllCategories(Pageable pageable) {
        return new ResponseEntity<>(Category.values(), HttpStatus.OK);
    }

    @GetMapping(value = "/categories/game", produces = {"application/json"}
    )
    public ResponseEntity<Object> getGamesByCategory(@RequestParam(required = true) String name,
                                                     @RequestParam(defaultValue = "10") int limit
                                                    ) {
        System.out.println("received data " + name);
        if (Category.fromString(name).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Page<gameData> gameRequest = this.gameService.getGameByCategory(Category.fromString(name).get(), limit);
        if (!gameRequest.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(gameRequest.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // search function
    @GetMapping(value = "/games/name", produces = {"application/json"}
    )
    public Optional<Object> getGame(@RequestParam String name) {
        System.out.println("received data " + String.valueOf(name));
        Optional<Object> gameRequest = this.gameService.getGame(name);
        if (gameRequest.isPresent()) {
            return gameRequest;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameRequest).getBody();
        }
    }

    @GetMapping(value = "/games/id", produces = {"application/json"})
    public Optional<Object> getGameById(@RequestParam String id) {
        System.out.println("received data " + id + "is type "+id.getClass());
        int ids = Integer.parseInt(id);
        Optional<Object> gameRequest = this.gameService.getGameById(ids);
        if (gameRequest.isPresent()) {
            return gameRequest;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameRequest).getBody();
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/admin/{name}", produces = {"application/json"}
    )
    public Optional<Object> getGameAuth(@RequestBody String name) {
        System.out.println("received data " + String.valueOf(name));
        Optional<Object> gameRequest = this.gameService.getGame(name);
        if (gameRequest.isPresent()) {
            return gameRequest;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gameRequest).getBody();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping({"/admin/newgame"})
    public ResponseEntity<?> postGame(@RequestBody @Valid gameDataDTO gameInput) {
        System.out.println("posting game "+gameInput);
        Instant now = Instant.now();
        LocalDateTime localNow = LocalDateTime.ofInstant(now, ZoneId.systemDefault());

        gameInput.setTimeAdded(localNow);

        int success = this.gameService.saveGame(gameInput);
        return success == 0 ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A game with this name already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class
    })
    public Map<String, Object> handleErrors(Exception ex) {

        if (ex instanceof MethodArgumentNotValidException validationEx) {
            Map<String, String> errors = new HashMap<>();
            validationEx.getBindingResult().getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return Map.of("validationErrors", errors);
        }

        if (ex instanceof HttpMessageNotReadableException) {
            return Map.of("error", "Malformed or missing JSON body");
        }

        return Map.of("error", "Unknown error");
    }
}
