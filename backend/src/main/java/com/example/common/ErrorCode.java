package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SYS_ERROR(1000, "System error"),
    AUTH_TOKEN_EXPIRED(1001, "Token expired"),
    USER_NOT_FOUND(1002, "User not found"),
    BAD_CREDENTIALS(1003, "Username or password incorrect"),
    ACCESS_DENIED(1004, "Access denied");

    private final int code;
    private final String message;
}
