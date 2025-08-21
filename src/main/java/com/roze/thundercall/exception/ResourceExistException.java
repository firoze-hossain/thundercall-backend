package com.roze.thundercall.exception;


import org.springframework.http.HttpStatus;

public class ResourceExistException extends RuntimeException {

    public ResourceExistException(String message) {
        super(message);

    }
}
