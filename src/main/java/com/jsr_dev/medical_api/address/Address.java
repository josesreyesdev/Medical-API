package com.jsr_dev.medical_api.address;

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
}
