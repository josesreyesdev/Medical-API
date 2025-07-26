package com.jsr_dev.medical_api.domain.appointment.validations.cancel;

import com.jsr_dev.medical_api.domain.appointment.Appointment;
import com.jsr_dev.medical_api.domain.appointment.AppointmentCancellationRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;

import java.time.LocalDateTime;

public class ValidatorToPreventCancelingPreviousAppointments implements AppointmentCancellationValidator {
    /*
    * Prevent canceling previous appointments
    * */

    private final AppointmentRepository appointmentRepository;

    public ValidatorToPreventCancelingPreviousAppointments(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(AppointmentCancellationRequest data) {
        LocalDateTime now = LocalDateTime.now();
        Appointment appointment = appointmentRepository.getReferenceById(data.appointmentId());
        LocalDateTime appointmentDate = appointment.getDate();

        if(appointmentDate.isBefore(now)) {
            throw new IntegrityValidationException("Past appointments cannot be canceled");
        }
    }
}
