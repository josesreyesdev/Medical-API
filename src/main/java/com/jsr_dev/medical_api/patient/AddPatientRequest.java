package com.jsr_dev.medical_api.patient;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.jsr_dev.medical_api.address.AddAddressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddPatientRequest(
        @NotBlank(message = "Name must not be empty.")
        String name,

        String avatar,

        @NotBlank(message = "Email must not be empty.")
        @Email(message = "Email must be a valid format.")
        String email,

        @NotBlank(message = "Identity document must not be empty.")
        @Pattern(regexp = "\\d{7,9}", message = "Identity document must contain between 7 and 9 digits.")
        String identityDocument,

        @JsonAlias({"phoneNumber", "phone_number"})
        @NotBlank(message = "Phone number must not be empty.")
        String phoneNumber,

        @JsonAlias({"address"})
        @NotNull(message = "Address must not be null.")
        @Valid
        AddAddressRequest addAddressRequest
) {
}
