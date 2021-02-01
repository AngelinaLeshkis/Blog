package com.example.blog.service;

import com.example.blog.entity.User;

public interface SendEmailService {

    void sendEmailToConfirmRegistration(String token, User user);

    void sendEmailToConfirmPasswordReset(String token, User user);

}
