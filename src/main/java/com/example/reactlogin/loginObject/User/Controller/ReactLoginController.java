//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.reactlogin.loginObject.User.Controller;

import com.example.reactlogin.loginObject.User.DTO.userDataDTO;
import com.example.reactlogin.loginObject.User.Service.ReactLoginService;
import com.example.reactlogin.loginObject.config.JwtUtil;
import com.example.reactlogin.loginObject.config.Roles;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api"})
public class ReactLoginController {
    @Autowired
    public ReactLoginService ReactLoginService;
    private final JwtUtil jwtUtil;

    public ReactLoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @GetMapping(
            value = {"/user/all"},
            produces = {"application/json"}
    )
    public ResponseEntity<List<userDataDTO>> getUsers(Pageable pageable) {
        return new ResponseEntity<>(this.ReactLoginService.getAllCustomers(pageable).getContent(), HttpStatus.OK);
    }

    @PostMapping(
            value = {"/user/login"},
            produces = {"application/json"}
    )
    public ResponseEntity<?> getUser(@RequestBody @Valid userDataDTO userInput) {
        System.out.println("received data " + String.valueOf(userInput));
        Optional<userDataDTO> userRequest = this.ReactLoginService.getCustomers(userInput);
        if (userRequest.isPresent()) {
            String token = this.jwtUtil.generateToken(userRequest.get().getUsername(), userRequest.get().getRole());
            ResponseCookie cookie = ResponseCookie.from("access_token", token).httpOnly(true).secure(true).path("/").sameSite("Strict").maxAge(900L).build();
            return ResponseEntity.ok().header("Set-Cookie", new String[]{cookie.toString()}).body(Map.of("username", userInput.getUsername(), "token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping({"/user/create"})
    public ResponseEntity<?> postUser(@RequestBody @Valid userDataDTO userInput) {
        System.out.println(userInput);
        int success = this.ReactLoginService.saveCustomer(userInput);
        return success == 0 ? ResponseEntity.status(HttpStatus.CREATED).build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A user with this name already exists");
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
