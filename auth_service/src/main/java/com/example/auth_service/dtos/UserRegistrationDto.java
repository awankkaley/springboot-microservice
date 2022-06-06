package com.example.auth_service.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegistrationDto {
    private String username;
    private String password;
}
