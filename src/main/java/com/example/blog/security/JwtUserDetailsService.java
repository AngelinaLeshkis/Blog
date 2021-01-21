package com.example.blog.security;


import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(JwtUserDetailsService.class);

    private UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("The user with email: " + email + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        logger.info("grant user " + jwtUser.getAuthorities());
        logger.info("grant user " + user.getRole().getValueOfRole());
        return jwtUser;
    }

}
