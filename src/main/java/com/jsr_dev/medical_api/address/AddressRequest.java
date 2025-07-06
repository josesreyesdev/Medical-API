package com.jsr_dev.medical_api.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequest(
        @NotBlank(message = "Street must not be empty.")
        String street,

        @NotBlank(message = "State or province must not be empty.")
        String stateOrProvince,

        @NotBlank(message = "Municipality or delegation must not be empty.")
        String municipalityOrDelegation,

        @NotBlank(message = "City must not be empty.")
        String city,

        @NotBlank (message = "Zip code must not be empty.")
        @Pattern(regexp = "\\d{4,8}", message = "Zip code must contain between 4 and 8 digits.")
        String zipCode,

        @NotBlank(message = "Country must not be empty.")
        String country,

        @NotBlank(message = "External number must not be empty.")
        String externalNumber,

        String internalNumber,
        String complement
) {
}