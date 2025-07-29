package com.example.parental_control_system.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(ResourceNotFoundException ex) {
        return new ApiError("NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(MaxChildrenReachedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMaxChildren(MaxChildrenReachedException ex) {
        return new ApiError("LIMIT_REACHED", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + " " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return new ApiError("VALIDATION_ERROR", msg);
    }
}
