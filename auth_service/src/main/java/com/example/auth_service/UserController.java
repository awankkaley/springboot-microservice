package com.example.auth_service;

import com.example.auth_service.dtos.LoginRequestDto;
import com.example.auth_service.dtos.TokenResponseDto;
import com.example.auth_service.dtos.UserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    public void regiter(@RequestBody UserRegistrationDto requestDto) {
        userService.save(requestDto);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }

}
