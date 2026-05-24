package com.example.reactlogin.loginObject.Auth.Service;


import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.User.Entity.userData;
import com.example.reactlogin.loginObject.User.Repository.userDataRepository;
import com.example.reactlogin.loginObject.config.Roles;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@Service
@Transactional
public class authService {

    @Autowired
    public userDataRepository userDataRepository;

    @Autowired
    public ModelMapper modelMapper;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public Page<userDataDTO> getAllCustomers(Pageable pageable){
        Pageable sortedByUploadDateDesc =
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by("id").descending());

        return userDataRepository.findAll(sortedByUploadDateDesc)
                .map(userData -> modelMapper.map(userData, userDataDTO.class));    }

    public Optional<userDataDTO> getCustomers(userDataDTO userData){
        Optional<userData> userOptional = userDataRepository.findByUsername(userData.getUsername());
        System.out.println("User found: "+ userOptional);
        System.out.println("Comparing with "+userData);

        if (userOptional.isPresent()) {
            userData user = userOptional.get();

            if (passwordEncoder.matches(userData.getPassword(), user.getPassword())) {
                return Optional.of(modelMapper.map(user, userDataDTO.class));
            }
        }

        return Optional.empty();
    }

    public int saveCustomer(userDataDTO customerDTO){
        Optional<userData> userOptional = userDataRepository.findByUsername(customerDTO.getUsername());

        if (userOptional.isEmpty()) {
            customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
            customerDTO.setRole(Roles.ROLE_USER);
            userDataRepository.save(modelMapper.map(customerDTO, userData.class));
            return 0;
        }
        else{
            return -1;
        }
    }


}
