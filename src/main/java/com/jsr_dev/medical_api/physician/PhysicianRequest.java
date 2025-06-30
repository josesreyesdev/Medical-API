package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.AddressRequest;

public record PhysicianRequest(
        String name,
        String avatar,
        String email,
        String phoneNumber,
        String document,
        Specialty specialty,
        AddressRequest addressRequest
) {
}