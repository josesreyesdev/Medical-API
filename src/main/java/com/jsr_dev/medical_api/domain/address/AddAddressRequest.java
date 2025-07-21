package com.jsr_dev.medical_api.domain.address;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddAddressRequest(
        @NotBlank(message = "is required")
        String street,

        @JsonAlias({"stateOrProvince", "state_or_province", "state", "province"})
        @NotBlank(message = "Is required")
        String stateOrProvince,

        @JsonAlias({"municipalityOrDelegation", "municipality_or_delegation", "municipality", "delegation"})
        @NotBlank(message = "Is required.")
        String municipalityOrDelegation,

        @NotBlank(message = "Is required")
        String city,

        @JsonAlias({"zipCode", "zip_code", "postalCode"})
        @NotBlank (message = "Is required")
        @Pattern(regexp = "\\d{4,8}", message = "Must contain between 4 and 8 digits.")
        String zipCode,

        @NotBlank(message = "Is required")
        String country,

        @JsonAlias({"externalNumber", "external_number", "ext_number"})
        @NotBlank(message = "Is required")
        String externalNumber,

        @JsonAlias({"internalNumber", "internal_number", "int_number"})
        String internalNumber,

        String complement
) {
}