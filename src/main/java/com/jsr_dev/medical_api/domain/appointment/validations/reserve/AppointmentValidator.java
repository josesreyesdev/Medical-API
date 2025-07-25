package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;

public interface AppointmentValidator {
    void validate(AddAppointmentRequest data);
}
