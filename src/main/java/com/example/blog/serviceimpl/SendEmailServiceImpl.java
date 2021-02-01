package com.example.blog.serviceimpl;

import com.example.blog.entity.User;
import com.example.blog.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender sender;

    @Autowired
    public SendEmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        sender.send(simpleMailMessage);
    }

    @Override
    public void sendEmailToConfirmRegistration(String token, User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s %s! \n" +
                            "Welcome to Blog. Please, visit the next link to activate your account: " +
                            "http://localhost:8080/auth/confirm/%s",
                    user.getFirstName(),
                    user.getLastName(),
                    token

            );
            sendEmail(user.getEmail(), message, "Activation code");
        }
    }

    @Override
    public void sendEmailToConfirmPasswordReset(String token, User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s %s! \n" +
                            "Please, visit the next link to confirm the password reset: " +
                            "http://localhost:8080/auth/checkCode/%s",
                    user.getFirstName(),
                    user.getLastName(),
                    token

            );
            sendEmail(user.getEmail(), message, "Reset password code");
        }
    }

}

