package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> {
    private int code;
    private String message;
    private T data;
    private String requestId;
    private long timestamp;

    public static <T> ResponseEntity<T> ok(T data, String requestId) {
        return new ResponseEntity<>(0, "success", data, requestId, Instant.now().toEpochMilli());
    }

    public static <T> ResponseEntity<T> fail(ErrorCode code, String requestId) {
        return new ResponseEntity<>(code.getCode(), code.getMessage(), null, requestId, Instant.now().toEpochMilli());
    }

    public static <T> ResponseEntity<T> fail(ErrorCode code, String message, String requestId) {
        return new ResponseEntity<>(code.getCode(), message, null, requestId, Instant.now().toEpochMilli());
    }
}
