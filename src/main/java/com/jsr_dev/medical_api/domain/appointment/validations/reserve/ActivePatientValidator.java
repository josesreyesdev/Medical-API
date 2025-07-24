package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.patient.PatientRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;

public class ActivePatientValidator implements AppointmentValidator {
    /*
     * Active patient validator
     * */

    private final PatientRepository patientRepository;

    public ActivePatientValidator(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void validate(AddAppointmentRequest data) {
        if (data.patientId() == null) {
            return;
        }

        Boolean isPatientActive = patientRepository.findActiveById(data.patientId());

        if (!isPatientActive) {
            throw new IntegrityValidationException("I can not schedule appointments with inactive patient");
        }
    }
}
