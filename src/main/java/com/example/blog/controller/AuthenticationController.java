package com.example.blog.controller;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private AuthorizationService authorizationService;

    @Autowired
    public AuthenticationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequestDTO requestDTO) {
        if (authorizationService.login(requestDTO) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationService.login(requestDTO).get("token");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
