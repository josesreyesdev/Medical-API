package com.jsr_dev.medical_api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String street;
    private String stateOrProvince;
    private String municipalityOrDelegation;
    private String city;
    private String zipCode;
    private String country;
    private String externalNumber;
    private String internalNumber;
    private String complement;

    public void update(AddAddressRequest update) {
        if (update.street() != null) this.street = update.street();
        if (update.stateOrProvince() != null) this.stateOrProvince = update.stateOrProvince();
        if (update.municipalityOrDelegation() != null) this.municipalityOrDelegation = update.municipalityOrDelegation();
        if (update.city() != null) this.city = update.city();
        if (update.zipCode() != null) this.zipCode = update.zipCode();
        if (update.country() != null) this.country = update.country();
        if (update.externalNumber() != null) this.externalNumber = update.externalNumber();
        if (update.internalNumber() != null) this.internalNumber = update.internalNumber();
        if (update.complement() != null) this.complement = update.complement();
    }
}
