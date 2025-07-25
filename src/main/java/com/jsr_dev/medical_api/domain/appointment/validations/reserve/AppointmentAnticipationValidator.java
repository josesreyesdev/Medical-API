package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentAnticipationValidator implements AppointmentValidator{
    /*
     * The appointment reservation, it cannot be less than 30 minutes.
     * */
    @Override
    public void validate(AddAppointmentRequest data) {
        LocalDateTime appointmentDate = data.date();
        LocalDateTime currentTime = LocalDateTime.now();
        long differenceInMinutes = Duration.between(currentTime, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new IntegrityValidationException("The appointment reservation, it cannot be less than 30 minutes.");
        }
    }
}
