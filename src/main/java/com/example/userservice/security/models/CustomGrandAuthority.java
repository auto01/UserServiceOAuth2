package com.example.userservice.security.models;

import com.example.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrandAuthority implements GrantedAuthority {

    private String authority;

    public CustomGrandAuthority(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
