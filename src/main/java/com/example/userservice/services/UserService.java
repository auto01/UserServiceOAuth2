package com.example.userservice.services;

import com.example.userservice.dto.UserSigninRequestDto;
import com.example.userservice.dto.UserSigninResponseDto;
import com.example.userservice.dto.UserSignupRequestDto;
import com.example.userservice.dto.UserSingupResponseDto;
import com.example.userservice.models.User;

public interface UserService {
    public UserSigninResponseDto login(UserSigninRequestDto userSigninRequestDto);
    public UserSingupResponseDto signup(UserSignupRequestDto userSignupRequestDto);
    public User validateToken(String token);
}
