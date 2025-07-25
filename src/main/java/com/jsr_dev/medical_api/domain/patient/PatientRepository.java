package com.jsr_dev.medical_api.domain.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findAllByActiveTrue(Pageable pageable);

    @Query("""
            SELECT p.active FROM Patient p
            WHERE p.id = :patientId
            """)
    Boolean findActiveById(Long patientId);
}
