package com.jsr_dev.medical_api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AddAppointmentRequest(
        @JsonAlias({"idPatient", "id_patient"})
        @NotNull(message = "is required")
        Long idPatient,

        @JsonAlias({"idPhysician", "id_physician"})
        Long idMedic,

        @JsonAlias({"date", "dateTime", "date_time"})
        @NotNull(message = "is required")
        @Future(message = "must be in the future")
        LocalDateTime dateTime
) {
}
