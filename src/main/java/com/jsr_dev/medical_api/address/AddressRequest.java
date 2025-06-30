package com.jsr_dev.medical_api.address;

public record AddressRequest(
        String street,
        String stateOrProvidence,
        String municipalityOrDelegation,
        String city,
        String zipCode,
        String country,
        String externalNumber,
        String internalNumber,
        String complement
) {
}