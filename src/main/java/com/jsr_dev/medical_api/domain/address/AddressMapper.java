package com.jsr_dev.medical_api.domain.address;

public class AddressMapper {
    public static Address mapToAddress(AddAddressRequest a) {
        return new Address(
                a.street(),
                a.stateOrProvince(),
                a.municipalityOrDelegation(),
                a.city(),
                a.zipCode(),
                a.country(),
                a.externalNumber(),
                a.internalNumber(),
                a.complement()
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
