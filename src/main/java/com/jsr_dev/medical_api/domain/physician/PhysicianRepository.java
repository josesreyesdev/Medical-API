package com.jsr_dev.medical_api.domain.physician;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PhysicianRepository extends JpaRepository<Physician, Long> {

    Page<Physician> findAllByActiveTrue(Pageable pageable);

    @Query(
            """
            SELECT p FROM Physician p
            WHERE p.active = true
            AND p.specialty = :specialty
            AND p.id NOT IN(
                SELECT a.physician.id FROM Appointment a
                WHERE a.date = :date
            )
            ORDER BY rand()
            LIMIT 1
        """
    )
    Optional<Physician> chooseARandomPhysicianAvailableOnTheDate(Specialty specialty, LocalDateTime date);

    @Query("""
            SELECT p.active FROM Physician p
            WHERE p.id = :physicianId
            """)
    Boolean findActiveById(Long physicianId);
}
