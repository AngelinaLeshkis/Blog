package com.example.blog.serviceimpl;

import com.example.blog.entity.User;
import com.example.blog.persistence.RedisRepository;
import com.example.blog.persistence.UserRepository;
import com.example.blog.pojo.VerificationToken;
import com.example.blog.service.ActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivationServiceImpl implements ActivationService {

    private final UserRepository userRepo;
    private final RedisRepository redisRepository;

    @Autowired
    public ActivationServiceImpl(UserRepository userRepo, RedisRepository redisRepository) {
        this.userRepo = userRepo;
        this.redisRepository = redisRepository;
    }

    @Override
    public boolean activateCode(String token) {
        Date date = new Date();
        VerificationToken verificationToken = redisRepository.getVerificationToken(token);
        if ((date.getTime() - verificationToken.getTimeOfCreation().getTime()) > VerificationToken.getExpiredTime()) {
            return false;
        }

        User user = userRepo.findById(verificationToken.getId()).orElse(null);

        if (user == null) {
            return false;
        }

        user.setEnabled(true);
        userRepo.save(user);

        return true;
    }

}
