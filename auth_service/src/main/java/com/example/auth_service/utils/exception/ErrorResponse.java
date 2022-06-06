package com.example.auth_service.utils.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getDetail())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toInvalidErrorResponseEntity(String errorMessage) {
        return ResponseEntity
                .status(400)
                .body(ErrorResponse.builder()
                        .status(400)
                        .error("BAD_REQUEST")
                        .code("BAD_REQUEST")
                        .message(errorMessage)
                        .build()
                );
    }
}