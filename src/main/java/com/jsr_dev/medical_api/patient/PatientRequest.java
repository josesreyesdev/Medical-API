package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.AddressRequest;

public record PatientRequest(
        String name,
        String email,
        String identityDocument,
        String phoneNumber,
        AddressRequest addressRequest
) {
}
