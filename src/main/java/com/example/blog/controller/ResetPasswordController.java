package com.example.blog.controller;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.dto.ResetPasswordDTO;
import com.example.blog.dto.UserDTO;
import com.example.blog.entity.User;
import com.example.blog.service.ActivationService;
import com.example.blog.service.RedisService;
import com.example.blog.service.UserService;
import com.example.blog.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class ResetPasswordController {

    private final UserService userService;
    private final SendEmailService sendEmailService;
    private final ActivationService activationService;
    private final RedisService redisService;

    @Autowired
    public ResetPasswordController(UserService userService, SendEmailService sendEmailService,
                                   ActivationService activationService, RedisService redisService) {
        this.userService = userService;
        this.sendEmailService = sendEmailService;
        this.activationService = activationService;
        this.redisService = redisService;
    }

    @PostMapping(value = "/forgotPassword")
    public String sendEmailWithTokenToResetPassword(@Valid @RequestBody
                                                            ResetPasswordDTO resetPasswordDTO) {
        User savedUser = userService.getUserByEmail(resetPasswordDTO.getEmail());

        if (savedUser == null) {
            return "Such user does not exist";
        }

        String token = redisService.createVerificationToken(savedUser);
        sendEmailService.sendEmailToConfirmPasswordReset(token, savedUser);

        return "Ok";
    }

    @PostMapping(value = "/reset")
    public ResponseEntity<UserDTO> resetPassword(@Valid @RequestBody
                                                         AuthenticationRequestDTO authenticationRequestDTO) {
        User userFromDB = userService.getUserByEmail(authenticationRequestDTO.getEmail());

        if (userFromDB.isEnabled()) {
            User savedUser = userService.setNewPassword(authenticationRequestDTO);
            UserDTO savedUserDTO = UserDTO.fromUser(savedUser);
            return new ResponseEntity<>(savedUserDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/checkCode/{code}")
    public String activate(@PathVariable(name = "code") String code) {
        boolean isActivated = activationService.activateCode(code);
        if (isActivated) {
            return "Password can be changed";
        } else {
            return "Activation code is invalid!";
        }
    }
}
