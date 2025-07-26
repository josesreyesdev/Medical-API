package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SameDayPatientAppointmentValidator implements AppointmentValidator{
    /*
    * No permita reservar mas de una consulta en el mismo día para el mismo paciente
    * Do not allow booking more than one appointment on the same day for the same patient.
    * */

    private final AppointmentRepository appointmentRepository;

    public SameDayPatientAppointmentValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(AddAppointmentRequest data) {

        LocalDateTime firstSchedule = data.date().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastSchedule = data.date().withHour(23).withMinute(59).withSecond(59);

        Boolean patientHasActiveAppointment = appointmentRepository
                .existsActivePatientAppointmentInRange(data.patientId(), firstSchedule, lastSchedule);

        if (patientHasActiveAppointment) {
            throw new IntegrityValidationException("The patient already has an active appointment for this day.");
        }
    }
}
