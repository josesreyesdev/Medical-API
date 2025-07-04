package com.jsr_dev.medical_api.patient;

import com.jsr_dev.medical_api.address.AddressMapper;

public class PatientMapper {
    public static Patient mapToPatient(PatientRequest patientRequest) {
        return new Patient(
                null,
                patientRequest.name(),
                patientRequest.email(),
                patientRequest.identityDocument(),
                patientRequest.phoneNumber(),
                AddressMapper.mapToAddress(patientRequest.addressRequest())
        );
    }
}
