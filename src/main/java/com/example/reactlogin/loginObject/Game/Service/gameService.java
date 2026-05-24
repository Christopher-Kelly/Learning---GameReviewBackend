package com.example.reactlogin.loginObject.Game.Service;


import com.example.reactlogin.loginObject.Game.DTO.Category;
import com.example.reactlogin.loginObject.Game.DTO.gameDataDTO;
import com.example.reactlogin.loginObject.Game.Entity.gameData;
import com.example.reactlogin.loginObject.Game.Repository.gameDataRepository;
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

@Service
@Transactional
public class gameService {

    @Autowired
    public gameDataRepository gameDataRepository;

    @Autowired
    public ModelMapper modelMapper;

    public Page<gameDataDTO> getAllGames(Pageable pageable, String param){
        Pageable sortedByUploadDateDesc =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(param));

        return gameDataRepository.findAll(sortedByUploadDateDesc)
                .map(gameData -> modelMapper.map(gameData, gameDataDTO.class));    }

    public Optional<Object> getGame(String gameName){
        Optional<gameData> gameOptional = gameDataRepository.findByName(gameName);
        System.out.println("game found: "+ gameOptional);
        System.out.println("Comparing with "+gameName);

        return Optional.ofNullable(gameOptional);

    }

    public Page<gameData> getGameByCategory (Category category, int limit){
        Pageable pageable = PageRequest.of(0, limit);
        Page<gameData> page = gameDataRepository.findByCategory(category, pageable);
        System.out.println("all games by "+ category + "= " +page) ;
        return page;

    }

    public int saveGame(gameDataDTO gameDTO){
        Optional<gameData> gameOptional = gameDataRepository.findByName(gameDTO.getName());

        if (gameOptional.isEmpty()) {
            gameDataRepository.save(modelMapper.map(gameDTO, gameData.class));
            return 0;
        }
        else{
            return -1;
        }
    }

    public Optional<Object> getGameById(int id){
        Optional<gameData> gameOptional = gameDataRepository.findById((long) id);
        System.out.println("game found: "+ gameOptional);

        return Optional.of(gameOptional);

    }
}
