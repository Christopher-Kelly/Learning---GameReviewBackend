package com.example.reactlogin.loginObject.config;

import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.User.Entity.userData;
import com.example.reactlogin.loginObject.User.Repository.userDataRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.modelmapper.ModelMapper;

import java.util.List;

@Component
@Profile("dev")
public class AllUserSeeder {

    @Autowired
    private userDataRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @PostConstruct
    public void init() {

        if (userRepository.count() > 0) return;

        List<String> usernames = List.of(
                "admin",
                "john_doe",
                "jane_smith",
                "gamer123",
                "proPlayer",
                "noobMaster",
                "pixelQueen",
                "shadowNinja",
                "casualGuy",
                "hardcore99"
        );

        for (int i = 0; i < usernames.size(); i++) {
            userDataDTO user = new userDataDTO();

            user.setUsername(usernames.get(i));
            user.setPassword(passwordEncoder.encode("password123"));

            if (i == 0) {
                user.setRole(Roles.ROLE_ADMIN);
            } else {
                user.setRole(Roles.ROLE_USER);
            }

            userRepository.save(modelMapper.map(user, userData.class));
        }
        System.out.println("User seeding done");
    }
}