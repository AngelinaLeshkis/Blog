package com.example.blog.service;

import com.example.blog.entity.User;

public interface ActivateUserAccountService {

    void sendEmailToConfirmRegistration(String token, User user);

    String createVerificationTokenForUser(User user);

    boolean activateCode(String token);

    void sendEmailToConfirmPasswordReset(String token, User user);

    User createEmailWithTokenToResetPassword(String email);
}
