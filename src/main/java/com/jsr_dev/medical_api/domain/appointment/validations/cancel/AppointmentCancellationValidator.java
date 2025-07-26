package com.jsr_dev.medical_api.domain.appointment.validations.cancel;

import com.jsr_dev.medical_api.domain.appointment.AppointmentCancellationRequest;

public interface AppointmentCancellationValidator {
    void validate(AppointmentCancellationRequest data);
}
