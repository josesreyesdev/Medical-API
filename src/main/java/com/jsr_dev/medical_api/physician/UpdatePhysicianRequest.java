package com.jsr_dev.medical_api.physician;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.address.AddAddressRequest;
import jakarta.validation.constraints.NotNull;

public record UpdatePhysicianRequest(
        @NotNull(message = "Id is required")
        Long id,
        String name,
        String avatar,

        @JsonAlias({"phoneNumber", "phone_number", "phone"})
        String phoneNumber,

        @JsonAlias("address")
        AddAddressRequest addAddressRequest
) {
}
