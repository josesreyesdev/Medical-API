package com.jsr_dev.medical_api.domain.appointment;

import com.jsr_dev.medical_api.domain.patient.Patient;
import com.jsr_dev.medical_api.domain.physician.Physician;

import java.time.LocalDateTime;

public class AppointmentMapper {
    public static Appointment toAppointment(Physician physician, Patient patient, LocalDateTime date){
        return new Appointment(
                null,
                physician,
                patient,
                date,
                null,
                null
        );
    }

    public static AppointmentResponse toAppointmentResponse(Appointment appointment){
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPhysician().getId(),
                appointment.getDate()
        );
    }
}
