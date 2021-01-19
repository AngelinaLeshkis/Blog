package com.example.blog.serviceimpl;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.persistence.UserRepository;
import com.example.blog.service.ActivateUserAccountService;
import com.example.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private ActivateUserAccountService activateUserAccountService;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder,
                           ActivateUserAccountService activateUserAccountService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.activateUserAccountService = activateUserAccountService;
    }

    @Override
    public User saveUser(User user) {
        User userFromDB = userRepo.findUserByEmail(user.getEmail());

        if (userFromDB != null) {
            return null;
        }

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User registeredUser = userRepo.save(user);
        String token = activateUserAccountService.createVerificationTokenForUser(registeredUser);
        activateUserAccountService.sendEmailToConfirmRegistration(token, registeredUser);

        return registeredUser;
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public Iterable<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new RuntimeException("User not found with id = " + id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }

    @Override
    public User setNewPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        User userFromDB = userRepo.findUserByEmail(authenticationRequestDTO.getEmail());

        if (userFromDB != null) {
            return null;
        }

        userFromDB.setPassword(passwordEncoder.encode(authenticationRequestDTO.getPassword()));
        userRepo.save(userFromDB);

        return userFromDB;
    }
}
