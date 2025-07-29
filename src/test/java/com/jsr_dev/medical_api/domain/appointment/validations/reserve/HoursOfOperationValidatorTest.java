package com.jsr_dev.medical_api.domain.appointment.validations.reserve;

import com.jsr_dev.medical_api.domain.appointment.AddAppointmentRequest;
import com.jsr_dev.medical_api.domain.physician.Specialty;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class HoursOfOperationValidatorTest {

    private final HoursOfOperationValidator validator = new HoursOfOperationValidator();

    @Test
    void shouldThrowExceptionIfAppointmentIsOnSunday() {
        // Arrange
        LocalDateTime sunday = LocalDateTime.of(2030, 12, 29, 10, 0); // Sunday
        AddAppointmentRequest request = new AddAppointmentRequest(1L, 1L, sunday, Specialty.CARDIOLOGY);

        // Act and Assert
        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(IntegrityValidationException.class)
                .hasMessageContaining("Opening hours are");
    }

    @Test
    void shouldThrowExceptionIfAppointmentIsBeforeOpeningHour() {
        LocalDateTime early = LocalDateTime.of(2030, Month.DECEMBER, 27, 6, 59); // Friday
        AddAppointmentRequest request = new AddAppointmentRequest(1L, 1L, early, Specialty.CARDIOLOGY);

        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(IntegrityValidationException.class);
    }

    @Test
    void shouldThrowExceptionIfAppointmentIsAfterClosingHour() {
        LocalDateTime late = LocalDateTime.of(2030, 12, 27, 18, 1); // Friday
        AddAppointmentRequest request = new AddAppointmentRequest(1L, 1L, late, Specialty.CARDIOLOGY);

        assertThatThrownBy(() -> validator.validate(request))
                .isInstanceOf(IntegrityValidationException.class);
    }

    @Test
    void shouldNotThrowExceptionIfAppointmentIsAtOpeningHour() {
        LocalDateTime valid = LocalDateTime.of(2030, 7, 25, 7, 0); // Wednesday
        AddAppointmentRequest request = new AddAppointmentRequest(1L, 1L, valid, Specialty.CARDIOLOGY);

        assertThatCode(() -> validator.validate(request))
                .doesNotThrowAnyException();
    }

    
}