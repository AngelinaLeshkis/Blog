package com.example.blog.serviceimpl;

import com.example.blog.entity.User;
import com.example.blog.persistence.RedisRepository;
import com.example.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    private final RedisRepository redisRepository;

    @Autowired
    public RedisServiceImpl(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public String createVerificationToken(User user) {
        return redisRepository.createVerificationTokenForUser(user);
    }
}
