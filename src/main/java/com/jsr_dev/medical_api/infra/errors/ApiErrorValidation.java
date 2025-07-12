package com.jsr_dev.medical_api.infra.errors;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorValidation(
        LocalDateTime timestamp,
        int status,
        String error,
        List<FieldErrorValidation> messages,
        String path
) {
}
