package com.example.blog.controller;

import com.example.blog.dto.UserDTO;
import com.example.blog.entity.User;
import com.example.blog.exception.BusinessException;
import com.example.blog.service.RedisService;
import com.example.blog.service.SendEmailService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        try {
            userService.saveUser(user);
            String token = redisService.createVerificationToken(user);
            sendEmailService.sendEmailToConfirmRegistration(token, user);
            UserDTO result = UserDTO.fromUser(user);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (BusinessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        try {
            UserDTO user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (BusinessException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
