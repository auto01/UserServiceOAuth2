package com.example.userservice.dto;

import com.example.userservice.models.Role;
import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;

}
