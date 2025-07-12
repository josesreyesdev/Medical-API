package com.jsr_dev.medical_api.domain.patient;

import com.jsr_dev.medical_api.domain.address.AddressResponse;

public record PatientResponse(
        Long id,
        String name,
        String avatar,
        String email,
        String identityDocument,
        String phoneNumber,
        AddressResponse address
) {
}
