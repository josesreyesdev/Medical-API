package com.jsr_dev.medical_api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorValidation> errorHandler404(EntityNotFoundException ex, HttpServletRequest request) {
        ApiErrorValidation error = new ApiErrorValidation(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of(new FieldErrorValidation("", ex.getMessage())),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorValidation> errorHandler400(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldError> errors = ex.getFieldErrors();
        List<FieldErrorValidation> fieldErrors = errors.stream()
                .map(FieldErrorValidation::new)
                .toList();

        ApiErrorValidation error = new ApiErrorValidation(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                fieldErrors,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
