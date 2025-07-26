package com.jsr_dev.medical_api.domain.appointment;

import com.jsr_dev.medical_api.domain.appointment.validations.cancel.AppointmentCancellationValidator;
import com.jsr_dev.medical_api.domain.appointment.validations.reserve.AppointmentValidator;
import com.jsr_dev.medical_api.domain.patient.Patient;
import com.jsr_dev.medical_api.domain.patient.PatientRepository;
import com.jsr_dev.medical_api.domain.physician.Physician;
import com.jsr_dev.medical_api.domain.physician.PhysicianRepository;
import com.jsr_dev.medical_api.domain.physician.Specialty;
import com.jsr_dev.medical_api.infra.exceptions.IntegrityValidationException;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentBookingService { /* AppointmentValidationService, AppointmentRulesValidator */

    private final AppointmentRepository appointmentRepository;
    private final PhysicianRepository physicianRepository;
    private final PatientRepository patientRepository;
    private final List<AppointmentValidator> validators;
    private final List<AppointmentCancellationValidator> cancellationValidators;

    public AppointmentBookingService(
            AppointmentRepository appointmentRepository,
            PhysicianRepository physicianRepository,
            PatientRepository patientRepository,
            List<AppointmentValidator> validators,
            List<AppointmentCancellationValidator> cancellationValidators
    ) {
        this.appointmentRepository = appointmentRepository;
        this.physicianRepository = physicianRepository;
        this.patientRepository = patientRepository;
        this.validators = validators;
        this.cancellationValidators = cancellationValidators;
    }

    public AppointmentResponse reserveAppointment(AddAppointmentRequest data) {
        Long patientId = data.patientId();
        if (!patientRepository.existsById(patientId)) {
            throw new IntegrityValidationException("Patient (ID: " + patientId + ") does not exist in the database.");
        }

        Long physicianId = data.physicianId();
        if (physicianId != null && !physicianRepository.existsById(physicianId)) {
            throw new IntegrityValidationException("Physician (ID: " + physicianId + ") does not exist in the database.");
        }

        /*
           Validators => it is part of Strategy Pattern
           SOLID principles:
           1.- Single responsibility
           2.- Open-closed
           3.- Dependency Inversion
         */
        validators.forEach(v -> v.validate(data));

        Physician physician = chooseAPhysician(data);
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IntegrityValidationException("Patient (ID: " + patientId + ") was not found in the database."));

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

    private Physician findPhysician(AddAppointmentRequest data) {
        Specialty specialty = data.specialty();
        if (specialty == null) {
            throw new IntegrityValidationException("A specialty is required when a physician id is not provided.");
        }
        return physicianRepository.chooseARandomPhysicianAvailableOnTheDate(specialty, data.date())
                .orElseThrow(() -> new ValidationException("No available physicians were found for the specified specialty: " + specialty + " and date"));
    }

    public void cancel(AppointmentCancellationRequest data) {
        Long appointmentId = data.appointmentId();
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IntegrityValidationException("Patient (ID: " + appointmentId + ") does not exist in the database.");
        }

        if (appointmentRepository.isAppointmentCancelled(appointmentId)) {
            throw new IntegrityValidationException("Appointment (ID: " + appointmentId + ") was already canceled.");
        }

        // Validators -> applied SOLID principles and strategy pattern
        cancellationValidators.forEach(v -> v.validate(data));

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new IntegrityValidationException("Appointment (ID: " + appointmentId + ") was not found in the database."));
        appointment.cancel(data.cancellationReason());
    }

    public AppointmentResponse getAppointmentById(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IntegrityValidationException("Appointment (ID: " + appointmentId + ") does not exist in the database.");
        }
        return AppointmentMapper.toAppointmentResponse(appointmentRepository.getReferenceById(appointmentId));
    }
}
