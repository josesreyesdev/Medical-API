package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.patient.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
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
    public ResponseEntity<PatientResponse> addPatient(
            @RequestBody @Valid AddPatientRequest patientRequest,
            UriComponentsBuilder uriBuilder
    ) {
        Patient patient = repository.save(PatientMapper.mapToPatient(patientRequest));

        URI uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
        PatientResponse patientResponse = PatientMapper.mapToPatientResponse(patient);

        return ResponseEntity.created(uri).body(patientResponse);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PatientResponse>>> getAllPatients(Pageable pageable) {
        Page<PatientResponse> page = repository.findAllByActiveTrue(pageable)
                .map(PatientMapper::mapToPatientResponse);

        PagedModel<EntityModel<PatientResponse>> pageModel = pagedResourcesAssembler.toModel(page, patientResponseModelAssembler);
        return ResponseEntity.ok(pageModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        Patient patient = repository.getReferenceById(id);
        return ResponseEntity.ok(PatientMapper.mapToPatientResponse(patient)); // 200
    }

    @Transactional
    @PutMapping
    public ResponseEntity<PatientResponse> updatePatient(@RequestBody @Valid UpdatePatientRequest updateData) {
        Patient patient = repository.getReferenceById(updateData.id());

        patient.update(updateData);

        return ResponseEntity.ok(PatientMapper.mapToPatientResponse(patient)); // 200
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePatient(@PathVariable Long id) {
        Patient patient = repository.getReferenceById(id);
        patient.deactivate();
        return ResponseEntity.noContent().build(); // 204
    }
}
