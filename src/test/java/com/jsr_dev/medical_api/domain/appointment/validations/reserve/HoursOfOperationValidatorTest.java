package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.physician.Specialty;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class HoursOfOperationValidatorTest {

    private final HoursOfOperationValidator validator = new HoursOfOperationValidator();

    @Test
    void shouldThrowExceptionIfAppointmentIsOnSunday() {
        LocalDateTime sunday = LocalDateTime.of(2030, 12, 29, 10, 0); // Sunday
        AddAppointmentRequest request = new AddAppointmentRequest(1L, 1L, sunday, Specialty.CARDIOLOGY);

        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(IntegrityValidationException.class)
                .hasMessageContaining("Opening hours are");
    }
}