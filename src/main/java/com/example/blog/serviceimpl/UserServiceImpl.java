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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
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
        Optional<User> userFromDB = userRepo.findUserByEmail(user.getEmail());

        if (userFromDB.isPresent()) {
            throw new BusinessException("User with email" + user.getEmail() + " has already existed!");
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
    public UserDTO getUserById(Long id) {
        return userRepo.findById(id)
                .map(UserDTO::fromUser)
                .orElseThrow(() -> new BusinessException("User", id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email " + email));
    }

    @Override
    public User setNewPassword(AuthenticationRequestDTO authenticationRequestDTO) {
        User userFromDB = userRepo.findUserByEmail(authenticationRequestDTO.getEmail())
                .orElseThrow(() -> new BusinessException("User not found with email " +
                        authenticationRequestDTO.getEmail()));

        if (userFromDB.isEnabled()) {
            userFromDB.setPassword(passwordEncoder.encode(authenticationRequestDTO.getPassword()));
            userRepo.save(userFromDB);
            return userFromDB;
        } else {

            throw new BusinessException("This operation is not available!");
        }

    }

    @Override
    public Long getLoggedInUserId() {
        JwtUser user = (JwtUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return user.getId();
    }
}
