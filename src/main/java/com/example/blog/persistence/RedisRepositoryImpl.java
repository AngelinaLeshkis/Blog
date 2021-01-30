package com.example.blog.persistence;

import com.example.blog.entity.User;
import com.example.blog.pojo.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private static final String KEY = "USER";
    private RedisTemplate<Long, String> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisRepositoryImpl(RedisTemplate<Long, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public String createVerificationTokenForUser(User user) {
        String activationCode = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user.getId(), new Date());
        hashOperations.put(KEY, activationCode, verificationToken);
        return activationCode;
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return (VerificationToken) hashOperations.get(KEY, token);
    }
}
