package com.test.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return new ResponseEntity<>(new DefaultExceptionResponse()
                .setMessage(e.getMessage())
                .setCause(Objects.nonNull(e.getCause()) ? e.getCause().getMessage() : ""), HttpStatus.BAD_REQUEST);
    }
}
