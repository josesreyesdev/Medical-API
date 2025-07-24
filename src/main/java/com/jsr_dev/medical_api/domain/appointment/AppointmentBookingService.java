package com.jsr_dev.medical_api.domain.appointment;

import com.jsr_dev.medical_api.domain.patient.Patient;
import com.jsr_dev.medical_api.domain.patient.PatientRepository;
import com.jsr_dev.medical_api.domain.physician.Physician;
import com.jsr_dev.medical_api.domain.physician.PhysicianRepository;
import com.jsr_dev.medical_api.domain.physician.Specialty;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import org.springframework.http.ResponseEntity;
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

        if (!patientRepository.existsById(data.patientId())) {
            throw new IntegrityValidationException("Patient (ID: " + data.patientId() + ") does not exist in the database.");
        }

        Long physicianId = data.physicianId();
        if (physicianId != null && !physicianRepository.existsById(physicianId)) {
            throw new IntegrityValidationException("Physician (ID: " + physicianId + ") does not exist in the database.");
        }

        Physician physician = chooseAPhysician(data);
        Patient patient = patientRepository.getReferenceById(data.patientId());/*.orElseThrow(() ->
                new IntegrityValidationException("Patient (ID: "+ data.patientId() +") was not found in the database.")
        );*/

        Appointment appointment = AppointmentMapper.toAppointment(physician, patient, data.date());
        appointmentRepository.save(appointment);

        return AppointmentMapper.toAppointmentResponse(appointment);
    }

    private Physician chooseAPhysician(AddAppointmentRequest data) {
        if (data.physicianId() != null) {
            return physicianRepository.getReferenceById(data.physicianId());
        }
        return findPhysician(data);
    }

    private Physician validateAndGetPhysician(Long physicianId) {
        if (!physicianRepository.existsById(physicianId)) {
            throw new IntegrityValidationException(
                    "The specified physician (ID: " + physicianId + ") does not exist in the database.");
        }

        if (!physicianRepository.findActiveById(physicianId)) {
            throw new IntegrityValidationException(
                    "The specified physician (ID: " + physicianId + ") is not active in the database.");
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

    public void cancel(AppointmentCancellationRequest cancellation) {
        Long appointmentId = cancellation.appointmentId();
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IntegrityValidationException("Patient (ID: " + appointmentId + ") does not exist in the database.");
        }

        Appointment appointment = appointmentRepository.getReferenceById(appointmentId);
        appointment.cancel(cancellation.cancellationReason());
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IntegrityValidationException("Appointment (ID: " + appointmentId + ") does not exist in the database.");
        }
        return AppointmentMapper.toAppointmentResponse(appointmentRepository.getReferenceById(appointmentId));
    }
}
