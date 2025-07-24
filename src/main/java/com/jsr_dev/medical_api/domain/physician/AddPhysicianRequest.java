package com.jsr_dev.medical_api.domain.physician;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.domain.address.AddAddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddPhysicianRequest(
        @NotBlank(message = "Is required")
        String name,

        String avatar,

        @NotBlank(message = "Is required")
        @Email(message = "Must be a valid format.")
        String email,

        @JsonAlias({"phoneNumber", "phone_number"})
        @NotBlank(message = "Is required")
        String phoneNumber,

        @NotBlank(message = "Is required")
        @Pattern(regexp = "\\d{7,9}", message = "Must contain between 7 and 9 digits.")
        String document,

        @NotNull(message = "Is required")
        Specialty specialty,

        @JsonAlias("address")
        @NotNull(message = "Is required")
        @Valid
        AddAddressRequest addAddressRequest
) {
}