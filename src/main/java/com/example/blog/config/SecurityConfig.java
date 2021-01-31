package com.example.blog.config;

import com.example.blog.security.JwtConfigurer;
import com.example.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/auth/**", "/articles/allPublicArticles",
                        "/articles/allPublicArticles/{id}", "/tags/**", "/tags-cloud",
                        "/articles", "/articles/page", "/articles/comments",
                        "articles/{articleId}/comments", "articles/{articleId}/comments/{id}",
                        "/articles/byTitle").permitAll()
                .antMatchers( "/articles/addArticle", "/articles/allUserArticles",
                        "/articles/updateArticle/**", "/articles/deleteArticle/**",
                        "articles/{articleId}/comments", "/{articleId}/comments/{id}").hasRole("USER")
                .antMatchers(  "/admin/**", "/articles/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}
