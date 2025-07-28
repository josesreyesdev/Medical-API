package com.jsr_dev.medical_api.domain.physician;

import com.jsr_dev.medical_api.domain.address.AddAddressRequest;
import com.jsr_dev.medical_api.domain.appointment.AppointmentMapper;
import com.jsr_dev.medical_api.domain.patient.AddPatientRequest;
import com.jsr_dev.medical_api.domain.patient.Patient;
import com.jsr_dev.medical_api.domain.patient.PatientMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PhysicianRepositoryTest {

    @Autowired
    private PhysicianRepository physicianRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Returns null when the physician has an appointment with another patient at that time.")
    void chooseARandomPhysicianAvailableOnTheDateScenario1() {
        // GIVEN
        LocalDateTime nexMondayAt10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0); // 10 am

        /* add a physician, patient and appointment in Database*/
        Physician physician = addPhysician(
                "Juan Physician",
                "juan-physician@example.com",
                "12443",
                Specialty.CARDIOLOGY
        );
        Patient patient = addPatient(
                "Test Patient",
                "patient-test@example.com",
                "21324243"
        );
        addAppointment(physician, patient, nexMondayAt10H);

        // WHEN
        // Physician is available
        Physician availablePhysician = physicianRepository
                .chooseARandomPhysicianAvailableOnTheDate(Specialty.CARDIOLOGY, nexMondayAt10H)
                .orElse(null);

        // THEN
        assertThat(availablePhysician).isNull();
    }

    @Test
    @DisplayName("Returns the physician available on that appointment date.")
    void chooseARandomPhysicianAvailableOnTheDateScenario2() {
        // GIVEN
        LocalDateTime nexMondayAt10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0); // 10 am

        /* add physician in Database*/
        Physician physician = addPhysician(
                "Juan Physician 2",
                "juan-physician-2@example.com",
                "12444",
                Specialty.DERMATOLOGY
        );

        // WHEN
        // Physician is available
        Physician availablePhysician = physicianRepository
                .chooseARandomPhysicianAvailableOnTheDate(Specialty.DERMATOLOGY, nexMondayAt10H)
                .orElse(null);

        // THEN
        assertThat(availablePhysician).isEqualTo(physician);
    }

    private void addAppointment(Physician physician, Patient patient, LocalDateTime date) {
        entityManager.persist(AppointmentMapper.toAppointment(physician, patient, date));
    }

    private Physician addPhysician(String name, String email, String document, Specialty specialty) {
        Physician physician = PhysicianMapper.mapToPhysician(physicianRequest(name, email, document, specialty));
        entityManager.persist(physician);
        return physician;
    }

    private Patient addPatient(String name, String email, String document) {
        Patient patient = PatientMapper.mapToPatient(patientRequest(name, email, document));
        entityManager.persist(patient);
        return patient;
    }

    private AddPhysicianRequest physicianRequest(String name, String email, String document, Specialty specialty) {
        return new AddPhysicianRequest(
                name,
                null,
                email,
                "1234123421",
                document,
                specialty,
                addressRequest()
        );
    }

    private AddPatientRequest patientRequest(String name, String email, String document) {
        return new AddPatientRequest(
                name,
                null,
                email,
                document,
                "1234123421",
                addressRequest()
        );
    }

    private AddAddressRequest addressRequest() {
        return new AddAddressRequest(
                "Principal Street 123",
                "Mexico City",
                "Benito Juárez",
                "Mexico City",
                "03100",
                "Mexico",
                "123",
                "A",
                "Department 4B"
        );
    }
}