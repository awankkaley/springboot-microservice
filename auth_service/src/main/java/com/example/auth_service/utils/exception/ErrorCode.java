package com.example.auth_service.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DATA_ALREADY_USER(CONFLICT, "data already exist"),
    USER_NOT_FOUND(NOT_FOUND, "user not found"),
    UNAUTHORIZED_USER(UNAUTHORIZED, "unauthorized user");


    private final HttpStatus httpStatus;
    private final String detail;
}