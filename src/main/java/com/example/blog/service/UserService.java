package com.example.blog.service;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.dto.UserDTO;
import com.example.blog.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<UserDTO> getUsers();

    User getUserById(Long id);

    User getUserByEmail(String email);

    User setNewPassword(AuthenticationRequestDTO authenticationRequestDTO);

    Long getLoggedInUserId();
}
