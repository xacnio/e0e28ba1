package com.example.amdscs.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class ApiError extends RuntimeException {
    @Getter @Setter
    private HttpStatus status;
    @Getter @Setter
    private String message;

    public ApiError() {}

    public ApiError(ApiErrors err) {
        this.status = err.getHttpResult();
        this.message = err.getErrorMessage();
    }

    public ApiError(HttpStatus status) {
        this.status = status;
    }
    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public ApiError(HttpStatus status, Throwable ex) {
        this.status = status;
        this.message = "Unexpected error";
    }
    public ApiError(HttpStatus status, String message, Throwable ex) {
        this.status = status;
        this.message = message;
    }
}