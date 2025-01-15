package com.example.userservice.dto;

import com.example.userservice.models.ResponseStatus;
import com.example.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSigninResponseDto {
    private String name;
    private Token token;
    private ResponseStatus status;
}
