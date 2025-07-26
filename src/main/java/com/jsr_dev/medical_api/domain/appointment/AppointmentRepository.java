package com.jsr_dev.medical_api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime firstSchedule, LocalDateTime lastSchedule);

    Boolean existsByPhysicianIdAndDate(Long physicianId, LocalDateTime date);

    @Query("""
            SELECT CASE WHEN a.cancellationReason IS NOT NULL THEN true ELSE false
            END FROM Appointment a
            WHERE a.id = :appointmentId
            """)
    Boolean isAppointmentCancelled(Long appointmentId);
}
