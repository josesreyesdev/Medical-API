package com.jsr_dev.medical_api.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByPatientIdAndDateBetween(Long patientId, LocalDateTime firstSchedule, LocalDateTime lastSchedule);

    Boolean existsByPhysicianIdAndDate(Long physicianId, LocalDateTime date);
}
