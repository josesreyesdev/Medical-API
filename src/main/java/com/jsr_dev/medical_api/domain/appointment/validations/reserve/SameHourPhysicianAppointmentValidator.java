package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.stereotype.Component;

@Component
public class SameHourPhysicianAppointmentValidator implements AppointmentValidator{
    /*
     * No permita reservar mas de una cita en la misma hora para el mismo medico
     * Do not allow booking more than one appointment on the same hour for the same physician.
    * */

    private final AppointmentRepository appointmentRepository;

    public SameHourPhysicianAppointmentValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(AddAppointmentRequest data) {
        Boolean isPhysicianAppointmentInTheSameHour = appointmentRepository
                .existByPhysicianIdAndDate(data.physicianId(), data.date());

        if (isPhysicianAppointmentInTheSameHour) {
            throw new IntegrityValidationException("The physician already has an active appointment on the same date and time.");
        }
    }
}
