package com.jsr_dev.medical_api.domain.appointment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CancellationReasonTest {

    @Test
    public void testFromEng() {
        assertEquals(CancellationReason.PATIENT_DESISTED, CancellationReason.fromEng("Patient desisted"));
        assertNull(CancellationReason.fromEng("Non-existent reason"));
    }

    @Test
    public void testFromEsp() {
        assertEquals(CancellationReason.PHYSICIAN_CANCELLATION, CancellationReason.fromEsp("Médico canceló"));
        assertNull(CancellationReason.fromEsp("Razón no existente"));
    }

    @Test
    public void testNormalize() {
        assertEquals("medico cancelo", CancellationReason.normalize("Médico canceló"));
        assertEquals("paciente desistio", CancellationReason.normalize("Paciente desistió"));
    }

    @Test
    public void testParseCancellationReason() {
        assertEquals(CancellationReason.OTHER, CancellationReason.parseCancellationReason("Other"));
        assertEquals(CancellationReason.PATIENT_DESISTED, CancellationReason.parseCancellationReason("Paciente desistió"));
    }

    @Test
    public void testExceptionHandling() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                CancellationReason.parseCancellationReason("Non-existent reason")
        );
        assertEquals("Cancellation Reason: Non-existent reason not found in enum class", exception.getMessage());
    }
}