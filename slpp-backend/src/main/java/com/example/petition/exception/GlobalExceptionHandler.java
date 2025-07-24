package com.example.petition.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        HttpStatus status;

        switch (ex.getErrorType()) {
            case INVALID_BIO_ID:
                status = HttpStatus.BAD_REQUEST;
                break;
            case DUPLICATE_BIO_ID:
            case DUPLICATE_EMAIL:
                status = HttpStatus.CONFLICT;
                break;
            case INVALID_CREDENTIALS:
                status = HttpStatus.UNAUTHORIZED;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(status).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralError(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", ex.getMessage()));
    }
}
