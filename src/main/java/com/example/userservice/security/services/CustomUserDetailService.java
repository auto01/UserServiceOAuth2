package com.example.userservice.security.services;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepo;
import com.example.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailService implements UserDetailsService {

    UserRepo userRepo;

    CustomUserDetailService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser=this.userRepo.findUserByEmail(email);
        if(optionalUser.isPresent()){
            User user=optionalUser.get();
            return new CustomUserDetails(user);

        }
        throw new UsernameNotFoundException(email);
    }
}
