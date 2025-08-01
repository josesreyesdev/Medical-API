package com.jsr_dev.medical_api.domain.patient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.domain.address.AddAddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddPatientRequest(
        @NotBlank(message = "Is required")
        String name,

        String avatar,

        @NotBlank(message = "Is required")
        @Email(message = "Must be a valid format.")
        String email,

        @NotBlank(message = "Is required")
        @Pattern(regexp = "\\d{7,9}", message = "Must contain between 7 and 9 digits.")
        String identityDocument,

        @JsonAlias({"phoneNumber", "phone_number"})
        @NotBlank(message = "Is required")
        String phoneNumber,

        @JsonAlias({"address"})
        @NotNull(message = "Is required")
        @Valid
        AddAddressRequest addAddressRequest
) {
}
