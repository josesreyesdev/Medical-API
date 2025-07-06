package com.jsr_dev.medical_api.physician;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.address.AddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PhysicianRequest(
        @NotBlank(message = "Name must not be empty.")
        String name,

        String avatar,

        @NotBlank(message = "Email must not be empty.")
        @Email(message = "Email must be a valid format.")
        String email,

        @JsonAlias({"phoneNumber", "phone_number"})
        @NotBlank(message = "Phone number must not be empty.")
        String phoneNumber,

        @NotBlank(message = "Document must not be empty.")
        @Pattern(regexp = "\\d{7,9}", message = "Document must contain between 7 and 9 digits.")
        String document,

        @NotNull(message = "Specialty must not be null")
        Specialty specialty,

        @JsonAlias("address")
        @NotNull(message = "Address must no be null") @Valid
        AddressRequest addressRequest
) {
}