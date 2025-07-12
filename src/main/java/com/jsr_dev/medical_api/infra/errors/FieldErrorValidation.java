package com.jsr_dev.medical_api.infra.errors;

import org.springframework.validation.FieldError;

public record FieldErrorValidation(String field, String message) {
    public FieldErrorValidation(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
