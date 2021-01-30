package com.example.blog.service;

import com.example.blog.entity.User;

public interface RedisService {

    String createVerificationToken(User user);
}
