package com.example.blog.serviceimpl;

import com.example.blog.dto.AuthenticationRequestDTO;
import com.example.blog.entity.User;
import com.example.blog.persistence.UserRepository;
import com.example.blog.security.JwtTokenProvider;
import com.example.blog.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthorizationServiceImpl(AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> login(AuthenticationRequestDTO authenticationRequestDTO) {
        try {
            String email = authenticationRequestDTO.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                    authenticationRequestDTO.getPassword()));
            User user = userRepository.findUserByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

            String token = jwtTokenProvider.createToken(email, user.getRole().getValueOfRole());
            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);

            return response;
        } catch (AuthenticationException e) {
            System.out.println(e);
            throw new BadCredentialsException("Invalid email or password", e);
        }
    }

}
