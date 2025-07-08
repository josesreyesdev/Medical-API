package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.AddressMapper;

public class PatientMapper {
    public static Patient mapToPatient(PatientRequest p) {
        return new Patient(
                null,
                p.name(),
                p.avatar(),
                p.email(),
                p.identityDocument(),
                p.phoneNumber(),
                AddressMapper.mapToAddress(p.addressRequest())
        );
    }

    public static PatientResponse mapToPatientResponse(Patient p) {
        return new PatientResponse(
                p.getName(),
                p.getAvatar(),
                p.getEmail(),
                p.getIdentityDocument(),
                p.getPhoneNumber(),
                AddressMapper.mapToAddressResponse(p.getAddress())
        );
    }
}
