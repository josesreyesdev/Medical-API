package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class HoursOfOperationValidator implements AppointmentValidator{

    /*
     * verify that it is not Sunday and that you can only consult
     * from Monday to Saturday from 7 am to 7 pm.
     * */

    @Override
    public void validate(AddAppointmentRequest data) {
        LocalDateTime appointmentDate = data.date();

        Boolean isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        Boolean isOpeningTime  = appointmentDate.getHour() < 7;
        Boolean isClosingTime = appointmentDate.getHour() > 18 ||
                (appointmentDate.getHour() == 18 && appointmentDate.getMinute() > 0);

        if (isSunday || isOpeningTime || isClosingTime) {
            throw new IntegrityValidationException("Opening hours are Monday to Saturday from 07:00 to 18:00 hours");
        }
    }
}
