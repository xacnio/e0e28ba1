package com.example.amdscs.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ErrorRes {
    @Schema(defaultValue = "false")
    private final boolean success = false;
    @Setter
    private String error;

    public ErrorRes(String error) {
        this.error = error;
    }
    public ResponseEntity<?> toResponse(HttpStatus statusCode) {
        return ResponseEntity.status(statusCode).body(this);
    }
}
