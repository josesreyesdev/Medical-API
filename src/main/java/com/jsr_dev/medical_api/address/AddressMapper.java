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

    public static AddressResponse mapToAddressResponse(Address a) {
        return new AddressResponse(
                a.getStreet(),
                a.getStateOrProvince(),
                a.getMunicipalityOrDelegation(),
                a.getCity(),
                a.getZipCode(),
                a.getCountry(),
                a.getExternalNumber(),
                a.getInternalNumber(),
                a.getComplement()
        );
    }
}
