package com.example.moviesplatform.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.status = HttpStatus.NOT_FOUND.value();
        err.error = HttpStatus.NOT_FOUND.getReasonPhrase();
        err.message = ex.getMessage();
        err.path = req.getRequestURI();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.status = HttpStatus.BAD_REQUEST.value();
        err.error = HttpStatus.BAD_REQUEST.getReasonPhrase();
        err.message = ex.getMessage();
        err.path = req.getRequestURI();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        err.error = HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase();
        err.message = "Validation failed";
        err.path = req.getRequestURI();
        err.details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        err.error = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        err.message = ex.getMessage();
        err.path = req.getRequestURI();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }
}

