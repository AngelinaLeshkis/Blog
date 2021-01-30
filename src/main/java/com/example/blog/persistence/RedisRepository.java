package com.example.blog.persistence;

import com.example.blog.entity.User;
import com.example.blog.pojo.VerificationToken;

public interface RedisRepository {

    String createVerificationTokenForUser(User user);

    VerificationToken getVerificationToken(String token);
}
