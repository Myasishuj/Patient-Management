package com.pm.authservice.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String token;
//    Constructor and setter same function when only one attribute
    public LoginResponseDto(String token) {
        this.token = token;
    }
}
