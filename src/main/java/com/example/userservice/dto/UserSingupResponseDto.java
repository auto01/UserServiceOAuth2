package com.example.userservice.dto;

import com.example.userservice.models.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSingupResponseDto {
    private Long id;
    private String name;
    private String email;
    private ResponseStatus status;
}
