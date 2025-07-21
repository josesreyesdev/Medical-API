package com.jsr_dev.medical_api.domain.appointment;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AddAppointmentRequest(
        @JsonAlias({"idPatient", "id_patient"})
        @NotNull(message = "Is required")
        Long idPatient,

        @JsonAlias({"idPhysician", "id_physician"})
        Long idPhysician,

        @NotNull(message = "Is required")
        @Future(message = "Must be in the future")
        LocalDateTime date
) {
}
