package com.jsr_dev.medical_api.domain.appointment;

import com.jsr_dev.medical_api.domain.physician.Specialty;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import com.jsr_dev.medical_api.domain.patient.Patient;
import com.jsr_dev.medical_api.domain.patient.PatientRepository;
import com.jsr_dev.medical_api.domain.physician.Physician;
import com.jsr_dev.medical_api.domain.physician.PhysicianRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentBookingService { /* AppointmentValidationService, AppointmentRulesValidator */

    private final AppointmentRepository appointmentRepository;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;

    public AppointmentBookingService(
            AppointmentRepository appointmentRepository,
            PhysicianRepository physicianRepository,
            PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.physicianRepository = physicianRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentResponse reserveAppointment(AddAppointmentRequest data) {

        if (!patientRepository.existsById(data.idPatient())) {
            throw new IntegrityValidationException("Patient (ID: "+ data.idPatient() +") does not exist in the database.");
        }

        Long physicianId = data.idPhysician();
        if(physicianId != null && !physicianRepository.existsById(physicianId)) {
            throw new IntegrityValidationException("Physician (ID: "+ physicianId +") does not exist in the database.");
        }

        Physician physician = chooseAPhysician(data);
        Patient patient = patientRepository.getReferenceById(data.idPatient());/*.orElseThrow(() ->
                new IntegrityValidationException("Patient (ID: "+ data.idPatient() +") was not found in the database.")
        );*/

        Appointment appointment = AppointmentMapper.toAppointment(physician, patient, data.date());
        appointmentRepository.save(appointment);

        return AppointmentMapper.toAppointmentResponse(appointment);
    }

    private Physician chooseAPhysician(AddAppointmentRequest data) {
        if (data.idPhysician() != null) {
            return physicianRepository.getReferenceById(data.idPhysician());
        }
        return findPhysician(data);
    }

    private Physician validateAndGetPhysician(Long physicianId) {
        if (!physicianRepository.existsById(physicianId)) {
            throw new IntegrityValidationException(
                    "The specified physician (ID: "+ physicianId +") does not exist in the database.");
        }

        if (!physicianRepository.findActiveById(physicianId)) {
            throw new IntegrityValidationException(
                    "The specified physician (ID: "+ physicianId +") is not active in the database.");
        }

        return physicianRepository.findById(physicianId)
                .orElseThrow(() -> new IllegalStateException(
                        "Physician disappeared after validation")); // Theoretically impossible case
    }

    private Physician findPhysician(AddAppointmentRequest data) {
        Specialty specialty = data.specialty();
        if (specialty == null) {
            throw new IntegrityValidationException("A specialty is required when a physician id is not provided.");
        }
        return physicianRepository.chooseARandomPhysicianAvailableOnTheDate(specialty, data.date())
                .orElseThrow(() -> new IntegrityValidationException("No available physicians were found for the specified specialty and date"));
    }
}
