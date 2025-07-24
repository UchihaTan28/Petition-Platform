package com.example.petition.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final ErrorType errorType;

    public CustomException(String message, ErrorType duplicateEmail) {
        super(message);
        this.errorType = duplicateEmail;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public enum ErrorType {
        INVALID_BIO_ID,
        DUPLICATE_BIO_ID,
        DUPLICATE_EMAIL,
        INVALID_CREDENTIALS, DUPLICATE_SIGNATURE, PETITION_NOT_FOUND, PETITION_CLOSED
    }
}
