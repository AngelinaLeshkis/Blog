package com.example.blog.controller;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.service.ActivationService;
import com.example.blog.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private final AuthorizationService authorizationService;
    private final ActivationService activationService;

    @Autowired
    public AuthenticationController(AuthorizationService authorizationService,
                                    ActivationService activationService) {
        this.authorizationService = authorizationService;
        this.activationService = activationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthenticationRequestDTO requestDTO) {
        if (authorizationService.login(requestDTO) == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authorizationService.login(requestDTO).get("token");
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping("/confirm/{code}")
    public String activate(@PathVariable(name = "code") String code) {
        boolean isActivated = activationService.activateCode(code);
        if (isActivated) {
            return "User successfully activated";
        } else {
            return "Activation code is invalid!";
        }
    }
}
