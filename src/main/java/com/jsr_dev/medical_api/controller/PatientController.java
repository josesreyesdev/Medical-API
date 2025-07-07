package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.patient.PatientMapper;
import com.jsr_dev.medical_api.patient.PatientRepository;
import com.jsr_dev.medical_api.patient.PatientRequest;
import com.jsr_dev.medical_api.patient.PatientResponse;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping
    public void addPatient(@RequestBody @Valid PatientRequest patientRequest) {
        repository.save(PatientMapper.mapToPatient(patientRequest));
    }

    @GetMapping
    public List<PatientResponse> getAllPatients() {
        return repository.findAll().stream()
                .map(PatientMapper::mapToPatientResponse)
                .collect(Collectors.toList());
    }
}
