package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.patient.PatientMapper;
import com.jsr_dev.medical_api.patient.PatientRepository;
import com.jsr_dev.medical_api.patient.PatientRequest;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
