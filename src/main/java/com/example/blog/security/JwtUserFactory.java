package com.example.blog.security;

import com.example.blog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getEmail(),
                user.getCreatedAt(),
                user.isEnabled(),
                mapToGrantedAuthorities(user.getRole().getValueOfRole())
        );
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(String userRole) {
        List<String> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        return userRoles.stream().map(role ->
                new SimpleGrantedAuthority(userRole)).collect(Collectors.toList());
    }

}
