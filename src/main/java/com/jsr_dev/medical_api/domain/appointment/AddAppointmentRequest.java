package com.jsr_dev.medical_api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.domain.physician.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AddAppointmentRequest(
        @JsonAlias({"patientId", "patient_id"})
        @NotNull(message = "Is required")
        Long patientId,

        @JsonAlias({"physicianId", "physician_id"})
        Long physicianId,

        @NotNull(message = "Is required")
        @Future(message = "Must be in the future")
        // @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // custom date time format
        LocalDateTime date, // 2025-07-21T02:00

        Specialty specialty
) {
}
