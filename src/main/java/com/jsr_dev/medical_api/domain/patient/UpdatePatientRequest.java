package com.jsr_dev.medical_api.domain.patient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.domain.address.AddAddressRequest;
import jakarta.validation.constraints.NotNull;

public record UpdatePatientRequest(
        @NotNull(message = "Id is required")
        Long id,
        String name,
        String avatar,
        String email,

        @JsonAlias({"phoneNumber", "phone_number", "phone"})
        String phoneNumber,

        @JsonAlias("address")
        AddAddressRequest addAddressRequest
) {
}
