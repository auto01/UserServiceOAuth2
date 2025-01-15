package com.example.userservice.controllers;

import com.example.userservice.dto.*;
import com.example.userservice.models.ResponseStatus;
import com.example.userservice.models.User;
import com.example.userservice.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;


    @PostMapping("/signup")
    public ResponseEntity<UserSingupResponseDto> signUp(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        try {
            UserSingupResponseDto userSingupResponseDt= userServiceImpl.signup(userSignupRequestDto);
            headers.add("status", userSingupResponseDt.getStatus().toString());
            return new ResponseEntity<>(userSingupResponseDt, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            headers.add("status", "error");
            return new ResponseEntity<>(null,headers, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<UserSigninResponseDto> login(@RequestBody UserSigninRequestDto userSigninRequestDto) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        try {
            UserSigninResponseDto userSigninResponseDto=userServiceImpl.login(userSigninRequestDto);
            headers.add("status",userSigninResponseDto.getStatus().toString());
            return new ResponseEntity<>(userSigninResponseDto, headers, HttpStatus.OK);
        } catch (Exception e) {
            headers.add("status", "login failed");
            return new ResponseEntity<>(null,headers, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable("token") String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        try{
            User user = userServiceImpl.validateToken(token);

            headers.add("token", token);
            if (user == null) {
                headers.add("status","User not found");
                return new ResponseEntity<>(headers,HttpStatus.UNAUTHORIZED);
            }
            headers.add("status","success");
            return new ResponseEntity<>(from(user),headers,HttpStatus.OK);
        }catch (Exception e){
        headers.add("status","failed");}
        return new ResponseEntity<>(null,headers,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<Void> logout(@PathVariable("token") String token) {
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("token", token);

        try {
            User user = userServiceImpl.validateToken(token);
            if (user == null) {
                headers.add("status","User not found");
                return new ResponseEntity<>(headers,HttpStatus.UNAUTHORIZED);
            }
            userServiceImpl.logout(token);
            headers.add("status", "success");
            return new ResponseEntity<>(headers,HttpStatus.OK);
        }catch (Exception e){
            headers.add("status", "failure");
            return new ResponseEntity<>(headers,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
