package com.jsr_dev.medical_api.infra.errors;

import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorValidation> errorHandler404(
            EntityNotFoundException ex,
            HttpServletRequest request
    ) {
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
    public ResponseEntity<ApiErrorValidation> errorHandler400(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
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

    /* REVIEW ERRORS */
    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationData>> errorHandler400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ErrorValidationData::new).toList());
    }*/

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> errorHandler400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> errorHandlerBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> errorHandlerAuthentication() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication Failure");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> errorHandlerDeniedAccess() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Denied access");
    }

    @ExceptionHandler(IntegrityValidationException.class)
    public ResponseEntity<String> errorHandlerValidationIntegrity403(IntegrityValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> errorHandlerBussinessRulesValidation403(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + ex.getLocalizedMessage());
    }

    public record ErrorValidationData(String field, String message) {
        public ErrorValidationData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
