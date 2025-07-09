package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.AddressResponse;

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
