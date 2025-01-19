package com.example.userservice.services;

import com.example.userservice.config.BcryptPasswordEncoderConfig;
import com.example.userservice.dto.UserSigninRequestDto;
import com.example.userservice.dto.UserSigninResponseDto;
import com.example.userservice.dto.UserSignupRequestDto;
import com.example.userservice.dto.UserSingupResponseDto;
import com.example.userservice.models.ResponseStatus;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepo;
import com.example.userservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BcryptPasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TokenRepo tokenRepo;

    @Override
    public UserSigninResponseDto login(UserSigninRequestDto userSigninRequestDto) {
        Optional<User> user=userRepo.findUserByEmail(userSigninRequestDto.getEmail());
        if(user.isPresent()){
            if(passwordEncoderConfig.getBCryptPasswordEncoder().matches(userSigninRequestDto.getPassword(),user.get().getEncodedPassword())){
                return from_login(user.get());
            }
            return null;
        }
        // if user is not present call signup
        return null;
    }

    @Override
    public UserSingupResponseDto signup(UserSignupRequestDto userSignupRequestDto) {
        Optional<User> userOptional = userRepo.findUserByEmail(userSignupRequestDto.getEmail());
        System.out.println("Inside service signup method");
        if (userOptional.isPresent()) {
            // user already present , navigate it to login page
            System.out.println("User already exists, please choose another one");
        }else{
            User user = new User();
            user.setEmail(userSignupRequestDto.getEmail());
            user.setName(userSignupRequestDto.getName());
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setDeleted(false);
            user.setEncodedPassword(passwordEncoderConfig.getBCryptPasswordEncoder().encode(userSignupRequestDto.getPassword()));
            userRepo.save(user);
            return from(user);
        }
        return null;
    }

    @Override
    public User validateToken(String token) {
        Optional<Token> t = tokenRepo.findByValueAndDeletedAndExpiryAtGreaterThan(token,false,new Date());
        // token expiry date must be greater than current time ,otherwise it is expired
        System.out.println("DEBUG");
        if (t.isPresent()) {
            System.out.println("token found");
            return t.get().getUser();
            }

        return null;
    }
    public void logout(String token) {
        Optional<Token> t=tokenRepo.findByValueAndDeleted(token,false);
        if(t.isPresent()){
            Token t1=t.get();
            t1.setDeleted(true); //soft delete
            tokenRepo.save(t1);
        }
    }

    UserSingupResponseDto from(User user) {
        UserSingupResponseDto responseDto = new UserSingupResponseDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        responseDto.setId(user.getId());
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }

    Token createToken(User user) {
        Token token = new Token();
        token.setCreatedAt(new Date());
        token.setUpdatedAt(new Date());
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString()); // this will store encrypted token
        token.setDeleted(false);
        //set toke expiry
        Instant now = Instant.now();
        Instant expiryInstant = now.plus(30, ChronoUnit.DAYS); // Add 30 days
        token.setExpiryAt(Date.from(expiryInstant)); // Set the expiry date

        return token;
    }

    UserSigninResponseDto from_login(User user) {
        UserSigninResponseDto responseDto = new UserSigninResponseDto();
        responseDto.setName(user.getName());

        //generate token
        Token token = createToken(user);

        //save token to db
        tokenRepo.save(token);

        responseDto.setToken(token);
        responseDto.setStatus(ResponseStatus.SUCCESS);
        return responseDto;
    }


}
