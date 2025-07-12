package com.jsr_dev.medical_api.domain.address;

public record AddressResponse(
        String street,
        String stateOrProvince,
        String municipalityOrDelegation,
        String city,
        String zipCode,
        String country,
        String externalNumber,
        String internalNumber,
        String complement
) {
}
