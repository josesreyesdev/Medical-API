package com.jsr_dev.medical_api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;

public record AppointmentCancellationRequest(
        @JsonAlias({"id", "appointment_id", "appointmentId"})
        @NotNull(message = "is required")
        Long appointmentId,

        @JsonAlias({"cancellationReason", "cancellation_reason"})
        @NotNull(message = "is required")
        CancellationReason cancellationReason
) {
}
