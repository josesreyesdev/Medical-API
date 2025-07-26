package com.jsr_dev.medical_api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
                SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
                FROM Appointment a
                WHERE a.patient.id = :patientId
                  AND a.cancellationReason IS NULL
                  AND a.date BETWEEN :start AND :end
            """)
    Boolean existsActivePatientAppointmentInRange(Long patientId, LocalDateTime start, LocalDateTime end);


    //Boolean existsByPhysicianIdAndDateAndCancellationReasonIsNull(Long physicianId, LocalDateTime date);
    @Query("""
                SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
                FROM Appointment a
                WHERE a.physician.id = :physicianId
                  AND a.cancellationReason IS NULL
                  AND a.date BETWEEN :start AND :end
            """)
    Boolean existsPhysicianAppointmentInRange(Long physicianId, LocalDateTime start, LocalDateTime end);


    @Query("""
            SELECT CASE WHEN a.cancellationReason IS NOT NULL THEN true ELSE false
            END FROM Appointment a
            WHERE a.id = :appointmentId
            """)
    Boolean isAppointmentCancelled(Long appointmentId);
}
