package com.example.blog.serviceimpl;

import com.example.blog.entity.User;
import com.example.blog.service.ActivateUserAccountService;
import org.springframework.stereotype.Service;

@Service
public class ActivateUserAccountServiceImpl implements ActivateUserAccountService {

    @Override
    public void sendEmailToConfirmRegistration(String token, User user) {

    }

    @Override
    public String createVerificationTokenForUser(User user) {
        return null;
    }

    @Override
    public boolean activateCode(String token) {
        return false;
    }

    @Override
    public void sendEmailToConfirmPasswordReset(String token, User user) {

    }

    @Override
    public User createEmailWithTokenToResetPassword(String email) {
        return null;
    }
}
