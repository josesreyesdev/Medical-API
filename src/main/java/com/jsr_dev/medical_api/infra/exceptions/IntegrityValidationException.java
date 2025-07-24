package com.jsr_dev.medical_api.infra.exceptions;

public class IntegrityValidationException extends RuntimeException {
    public IntegrityValidationException(String message) {
        super(message);
    }
}
