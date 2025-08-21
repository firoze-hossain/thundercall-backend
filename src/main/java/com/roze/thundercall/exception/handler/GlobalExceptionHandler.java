package com.roze.thundercall.exception.handler;

import com.roze.thundercall.exception.ResourceExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(ResourceExistException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(ex.getMessage());
        response.setTimestamp(Instant.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }
}
