package com.Auth_Service.Exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        ApiError error = new ApiError(
                "UNAUTHORIZED",
                "Invalid email or password",
                request.getRequestURI(),
                Instant.now(),
                MDC.get("traceId")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

        log.warn("Resource not found : {}", ex.getMessage());

        ApiError error=new ApiError(
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                MDC.get("traceId")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateResourceException ex, HttpServletRequest request) {
        log.warn("Duplicate resource : {}", ex.getMessage());
        ApiError error = new ApiError(
                "DUPLICATE_RESOURCE",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                MDC.get("traceId")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest request) {
        log.warn("Bad request : {}", ex.getMessage());
        ApiError error = new ApiError(
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now(),
                MDC.get("traceId")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
