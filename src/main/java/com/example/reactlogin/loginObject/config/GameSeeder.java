package com.example.reactlogin.loginObject.config;

import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Game.Repository.gameDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
@Component
public class GameSeeder {

    @Autowired
    private gameDataRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper;

    public GameSeeder() {
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules(); // <-- THIS fixes LocalDateTime
    }

    @PostConstruct
    public void seed() {
        try {
            InputStream inputStream =
                    new ClassPathResource("games.json").getInputStream();

            List<gameDataDTO> games = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<gameDataDTO>>() {}
            );

            for (gameDataDTO dto : games) {
                repository.save(modelMapper.map(dto, gameData.class));
            }

            System.out.println("Game seeding done");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}