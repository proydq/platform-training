package com.example.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Void> handleBusiness(BusinessException ex, HttpServletRequest request) {
        log.error("Business exception", ex);
        return ResponseEntity.fail(ex.getErrorCode(), request.getHeader("X-Request-ID"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.error("Access denied", ex);
        return ResponseEntity.fail(ErrorCode.ACCESS_DENIED, request.getHeader("X-Request-ID"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception ex, HttpServletRequest request) {
        log.error("System error", ex);
        return ResponseEntity.fail(ErrorCode.SYS_ERROR, request.getHeader("X-Request-ID"));
    }
}
