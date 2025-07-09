package com.jsr_dev.medical_api.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAddressRequest(
        @NotBlank(message = "Street must not be empty.")
        String street,

        @JsonAlias({"stateOrProvince", "state_or_province", "state", "province"})
        @NotBlank(message = "State or province must not be empty.")
        String stateOrProvince,

        @JsonAlias({"municipalityOrDelegation", "municipality_or_delegation", "municipality", "delegation"})
        @NotBlank(message = "Municipality or delegation must not be empty.")
        String municipalityOrDelegation,

        @NotBlank(message = "City must not be empty.")
        String city,

        @JsonAlias({"zipCode", "zip_code", "postalCode"})
        @NotBlank (message = "Zip code must not be empty.")
        @Pattern(regexp = "\\d{4,8}", message = "Zip code must contain between 4 and 8 digits.")
        String zipCode,

        @NotBlank(message = "Country must not be empty.")
        String country,

        @JsonAlias({"externalNumber", "external_number", "ext_number"})
        @NotBlank(message = "External number must not be empty.")
        String externalNumber,

        @JsonAlias({"internalNumber", "internal_number", "int_number"})
        String internalNumber,

        String complement
) {
}