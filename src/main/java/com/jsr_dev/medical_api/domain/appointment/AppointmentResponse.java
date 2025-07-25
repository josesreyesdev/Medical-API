package com.jsr_dev.medical_api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentResponse(
        Long id,
        Long patientId,
        Long physicianId,
        LocalDateTime date
) {
}
