package com.example.blog.serviceimpl;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.dto.UserDTO;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.exception.BusinessException;
import com.example.blog.persistence.UserRepository;
import com.example.blog.security.JwtUser;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        User userFromDB = userRepo.findUserByEmail(user.getEmail());

        if (userFromDB != null) {
            return null;
        }

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepo.findAll().stream()
                .map(UserDTO::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new BusinessException("User not found with id = " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public User setNewPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        User userFromDB = userRepo.findUserByEmail(authenticationRequestDTO.getEmail());

        if (userFromDB == null) {
            return null;
        }

        userFromDB.setPassword(passwordEncoder.encode(authenticationRequestDTO.getPassword()));
        userRepo.save(userFromDB);

        return userFromDB;
    }

    @Override
    public Long getLoggedInUserId() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getId();
    }
}
