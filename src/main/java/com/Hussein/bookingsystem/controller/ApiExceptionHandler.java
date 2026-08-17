package com.Hussein.bookingsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationErrors(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Validation failed");
        response.put("errors", fieldErrors);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleNotFound(IllegalArgumentException exception) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", exception.getMessage());
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}
