package com.example.blog.service;

import com.example.blog.dto.AuthenticationRequestDTO;

import java.util.Map;

public interface AuthorizationService {

    Map<String, String> login(AuthenticationRequestDTO authenticationRequestDTO);
}

