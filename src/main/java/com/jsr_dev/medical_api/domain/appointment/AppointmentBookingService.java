package com.jsr_dev.medical_api.domain.appointment;

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
            throw new IllegalArgumentException("The specified patient (ID: "+ data.idPatient() +") does not exist in the database.");
        }

        Patient patient = patientRepository.findById(data.idPatient()).get();
        Physician physician = physicianRepository.findById(data.idPhysician()).get();

        Appointment appointment = AppointmentMapper.toAppointment(physician, patient, data.date());

        appointmentRepository.save(appointment);

        return AppointmentMapper.toAppointmentResponse(appointment);
    }
}
