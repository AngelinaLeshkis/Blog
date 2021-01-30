package com.example.blog.controller;

import com.example.blog.dto.UserDTO;
import com.example.blog.entity.User;
import com.example.blog.service.RedisService;
import com.example.blog.service.UserService;
import com.example.blog.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final SendEmailService sendEmailService;
    private final RedisService redisService;

    @Autowired
    public UserController(UserService userService, SendEmailService sendEmailService, RedisService redisService) {
        this.userService = userService;
        this.sendEmailService = sendEmailService;
        this.redisService = redisService;
    }

    @PostMapping(value = "/registration/user")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody User user) {
        return Optional.ofNullable(userService.saveUser(user))
                .map(inputUser -> {
                    String token = redisService.createVerificationToken(user);
                    sendEmailService.sendEmailToConfirmRegistration(token, user);
                    UserDTO result = UserDTO.fromUser(user);
                    return new ResponseEntity<>(result, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDTO result = UserDTO.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



}
