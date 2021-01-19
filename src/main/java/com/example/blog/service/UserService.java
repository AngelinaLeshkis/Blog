package com.example.blog.service;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.entity.User;

public interface UserService {

    User saveUser(User user);

    void updateUser(User user);

    Iterable<User> getUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User setNewPassword(AuthenticationRequestDTO authenticationRequestDTO);
}
