package com.example.reactlogin.loginObject.Auth.DTO;

import com.example.reactlogin.loginObject.config.Roles;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class authDataDTO {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;

    private String phoneNumber;
    private String bio;

    private Roles role;
}
