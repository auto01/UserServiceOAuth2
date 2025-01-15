package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninRequestDto {
    private String email;
    private String password;
}
