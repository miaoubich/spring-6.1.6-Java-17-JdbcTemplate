package com.miaoubich.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    public StudentNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
