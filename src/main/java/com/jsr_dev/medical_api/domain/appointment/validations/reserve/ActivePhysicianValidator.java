package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.physician.PhysicianRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;

public class ActivePhysicianValidator implements AppointmentValidator{
    /*
     * Active patient validator
     * */

    private final PhysicianRepository physicianRepository;

    public ActivePhysicianValidator(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    @Override
    public void validate(AddAppointmentRequest data) {
        if (data.physicianId() == null) {
            return;
        }

        Boolean isPhysicianActive = physicianRepository.findActiveById(data.physicianId());

        if (!isPhysicianActive) {
            throw new IntegrityValidationException("I can not schedule appointments with inactive physician");
        }
    }
}
