package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.AddressMapper;

public class PatientMapper {
    public static Patient mapToPatient(AddPatientRequest p) {
        return new Patient(
                null,
                p.name(),
                p.avatar(),
                p.email(),
                p.identityDocument(),
                p.phoneNumber(),
                AddressMapper.mapToAddress(p.addAddressRequest()),
                true
        );
    }

    public static PatientResponse mapToPatientResponse(Patient p) {
        return new PatientResponse(
                p.getId(),
                p.getName(),
                p.getAvatar(),
                p.getEmail(),
                p.getIdentityDocument(),
                p.getPhoneNumber(),
                AddressMapper.mapToAddressResponse(p.getAddress())
        );
    }
}
