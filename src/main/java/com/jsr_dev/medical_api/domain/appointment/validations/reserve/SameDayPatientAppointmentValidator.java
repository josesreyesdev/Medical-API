package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;

import java.time.LocalDateTime;

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
        LocalDateTime firstSchedule = data.date().withHour(7);
        LocalDateTime lastSchedule = data.date().withHour(18);

        Boolean patientWithAppointment = appointmentRepository
                .existsByPatientIdAndDateBetween(data.patientId(), firstSchedule, lastSchedule);

        if (patientWithAppointment) {
            throw new IntegrityValidationException("The patient already has an active appointment for this day.");
        }
    }
}
