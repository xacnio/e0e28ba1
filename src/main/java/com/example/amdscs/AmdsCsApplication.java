package com.example.amdscs;

import com.example.amdscs.api.advice.RestExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RestExceptionHandler.class)
public class AmdsCsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmdsCsApplication.class, args);
    }
}
