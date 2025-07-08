package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.AddressResponse;

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
