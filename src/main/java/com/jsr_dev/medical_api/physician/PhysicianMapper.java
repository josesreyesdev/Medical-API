package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.AddressMapper;

public class PhysicianMapper {
    public static Physician mapToPhysician(PhysicianRequest physicianRequest) {
        return new Physician(
                null,
                physicianRequest.name(),
                physicianRequest.avatar(),
                physicianRequest.email(),
                physicianRequest.phoneNumber(),
                physicianRequest.document(),
                physicianRequest.specialty(),
                AddressMapper.mapToAddress(physicianRequest.addressRequest())
        );
    }
}
