package com.jsr_dev.medical_api.domain.appointment.validations.cancel;

import com.jsr_dev.medical_api.domain.appointment.Appointment;
import com.jsr_dev.medical_api.domain.appointment.AppointmentCancellationRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentRepository;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentCancellationAnticipationValidator implements AppointmentCancellationValidator {
    /*
    * Cancelación de cita con 24 horas de antelación
    * Appointment cancellation with 24 hours in advance
    * */

    private final AppointmentRepository appointmentRepository;

    public AppointmentCancellationAnticipationValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public void validate(AppointmentCancellationRequest data) {
        Long appointmentId = data.appointmentId();
        Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        LocalDateTime now = LocalDateTime.now();
        long differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new IntegrityValidationException("Appointment (ID: " + appointmentId + ") can only be canceled at least 24 hours in advance.");
        }
    }
}
