package com.jsr_dev.medical_api.physician;

import com.jsr_dev.medical_api.address.AddressMapper;

public class PhysicianMapper {
    public static Physician mapToPhysician(PhysicianRequest p) {
        return new Physician(
                null,
                p.name(),
                p.avatar(),
                p.email(),
                p.phoneNumber(),
                p.document(),
                p.specialty(),
                AddressMapper.mapToAddress(p.addressRequest())
        );
    }

    public static PhysicianResponse mapToPhysicianResponse(Physician p) {
        return new PhysicianResponse(
                p.getId(),
                p.getName(),
                p.getAvatar(),
                p.getEmail(),
                p.getPhoneNumber(),
                p.getSpecialty(),
                p.getDocument(),
                AddressMapper.mapToAddressResponse(p.getAddress())
        );
    }
}
