package com.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

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

    /* ========== 成功 ========== */
    public static <T> ResponseEntity<T> ok(T data) {
        return ok(data, null);
    }

    public static <T> ResponseEntity<T> ok(T data, String reqId) {
        return new ResponseEntity<>(0, "success", data, defaultReqId(reqId), Instant.now().toEpochMilli());
    }

    /* ========== 失败 ========== */
    public static <T> ResponseEntity<T> fail(ErrorCode code) {
        return fail(code, null);
    }

    public static <T> ResponseEntity<T> fail(ErrorCode code, String reqId) {
        return new ResponseEntity<>(code.getCode(), code.getMessage(), null,
                defaultReqId(reqId), Instant.now().toEpochMilli());
    }

    public static <T> ResponseEntity<T> fail(ErrorCode code, String message, String reqId) {
        return new ResponseEntity<>(code.getCode(), message, null,
                defaultReqId(reqId), Instant.now().toEpochMilli());
    }

    /* ========== 共用兜底 ========== */
    private static String defaultReqId(String reqId) {
        return (reqId != null && !reqId.isEmpty()) ? reqId : MDC.get("requestId");
    }
}
