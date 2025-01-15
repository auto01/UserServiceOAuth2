package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// this will disable spring security
// spring security secures each endpoint defined

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable CSRF and allow all requests
        http.csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll(); // Allow all requests without authentication

        return http.build();
    }
}
