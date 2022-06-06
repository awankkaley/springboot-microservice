package com.example.auth_service;

import com.example.auth_service.config.TokenProvider;
import com.example.auth_service.dtos.LoginRequestDto;
import com.example.auth_service.dtos.TokenResponseDto;
import com.example.auth_service.dtos.UserRegistrationDto;
import com.example.auth_service.entities.Authority;
import com.example.auth_service.entities.User;
import com.example.auth_service.repositories.UserRepository;
import com.example.auth_service.utils.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.example.auth_service.utils.exception.ErrorCode.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @Transactional
    public Long save(UserRegistrationDto requestDto) {
        log.error(requestDto.getUsername());
        if (userRepository.findByUsername(requestDto.getUsername()).orElse(null) != null) {
            throw new CustomException(DATA_ALREADY_USER);
        }

        Authority authority = Authority.builder()
                .authorityName("service1")
                .build();

        User user = User.builder()
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .build();

        return userRepository.save(user).getId();
    }


    @Transactional
    public TokenResponseDto login(LoginRequestDto requestDto) {

        User currentUser = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String currentPassword = currentUser.getPassword();

        boolean isPasswordValid = BCrypt.checkpw(requestDto.getPassword(), currentPassword);

        if (!isPasswordValid) throw new CustomException(UNAUTHORIZED_USER);

        return tokenProvider.generateTokenDtoByUser(currentUser);
    }
}
