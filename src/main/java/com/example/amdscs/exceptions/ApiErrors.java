package com.example.amdscs.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrors {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "bad request"),
    AIRPORT_NOT_FOUND(HttpStatus.NOT_FOUND, 1000, "airport not found"),
    FLIGHT_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "flight not found"),
    ARR_DEP_CONFLICT(HttpStatus.BAD_REQUEST, 2001, "arrival and departure airports must not be the same");

    private final HttpStatus httpResult;
    private final int code;
    private final String errorMessage;

    ApiErrors(HttpStatus httpResult, int code, String errorMessage) {
        this.httpResult = httpResult;
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
