package com.example.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcryptPasswordEncoderConfig {

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*
    When you define BCryptPasswordEncoder as a Spring Bean,
    you can inject it into any service or component in your application using @Autowired, constructor injection, or setter injection.
    This promotes loose coupling between components and makes the code more modular and testable.
     */
}
