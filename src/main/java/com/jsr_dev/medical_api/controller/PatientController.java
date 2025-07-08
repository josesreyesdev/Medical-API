package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.patient.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientRepository repository;

    // Convert Page to PageModel
    private final PagedResourcesAssembler<PatientResponse> pagedResourcesAssembler;

    // Convert PhysicianResponse to EntityModel
    private final PatientResponseModelAssembler patientResponseModelAssembler;

    public PatientController(
            PatientRepository repository,
            PagedResourcesAssembler<PatientResponse> pagedResourcesAssembler,
            PatientResponseModelAssembler patientResponseModelAssembler
    ) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.patientResponseModelAssembler = patientResponseModelAssembler;
    }

    @Transactional
    @PostMapping
    public void addPatient(@RequestBody @Valid PatientRequest patientRequest) {
        repository.save(PatientMapper.mapToPatient(patientRequest));
    }

    @GetMapping
    public PagedModel<EntityModel<PatientResponse>> getAllPatients(Pageable pageable) {
        Page<PatientResponse> page = repository.findAll(pageable)
                .map(PatientMapper::mapToPatientResponse);
        return pagedResourcesAssembler.toModel(page, patientResponseModelAssembler);
    }
}
