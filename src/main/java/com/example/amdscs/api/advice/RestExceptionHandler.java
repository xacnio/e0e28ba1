package com.example.amdscs.api.advice;

import com.example.amdscs.exceptions.ApiError;
import com.example.amdscs.dto.responses.ErrorRes;
import org.springdoc.api.ErrorMessage;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ApiError.class)
    @ResponseBody
    public Object processValidationError(ApiError ex) {
        String result = ex.getMessage();
        return new ErrorRes(result).toResponse(ex.getStatus());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Object conflict(DataIntegrityViolationException e) {
        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        // ERROR: duplicate key value violates unique constraint \"uk_dvlvh1tbph2ol9ewes6e8swtt\"\n  Detail: Key (city)=(test) already exists.
        message = message.replaceAll("(.*)\\n(.*)\\: Key \\((.*)\\)\\=\\((.*)\\)(.*)", "($4)");
        message += " is conflicts";
        return new ErrorRes(message).toResponse(HttpStatus.CONFLICT);
    }
}