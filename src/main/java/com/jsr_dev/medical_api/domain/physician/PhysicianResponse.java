package com.jsr_dev.medical_api.domain.physician;

import com.jsr_dev.medical_api.domain.address.AddressResponse;

public record PhysicianResponse(
        Long id,
        String name,
        String avatar,
        String email,
        String phoneNumber,
        Specialty specialty,
        String document,
        AddressResponse address
) {
}
