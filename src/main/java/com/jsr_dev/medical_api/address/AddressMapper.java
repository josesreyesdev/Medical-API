package com.jsr_dev.medical_api.address;

public class AddressMapper {
    public static Address mapToAddress(AddressRequest addressRequest) {
        return new Address(
                addressRequest.street(),
                addressRequest.stateOrProvince(),
                addressRequest.municipalityOrDelegation(),
                addressRequest.city(),
                addressRequest.zipCode(),
                addressRequest.country(),
                addressRequest.externalNumber(),
                addressRequest.internalNumber(),
                addressRequest.complement()
        );
    }
}
