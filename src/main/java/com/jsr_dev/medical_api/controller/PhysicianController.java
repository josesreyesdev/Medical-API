package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.domain.physician.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/physicians")
@SecurityRequirement(name = "bearer-key")
public class PhysicianController {

    private final PhysicianRepository repository;

    // Convert Page to PageModel
    private final PagedResourcesAssembler<PhysicianResponse> pagedResourcesAssembler;

    // Convert PhysicianResponse to EntityModel
    private final PhysicianResponseModelAssembler physicianResponseModelAssembler;

    public PhysicianController(
            PhysicianRepository repository,
            PagedResourcesAssembler<PhysicianResponse> pagedResourcesAssembler,
            PhysicianResponseModelAssembler physicianResponseModelAssembler
    ) {
        this.repository = repository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.physicianResponseModelAssembler = physicianResponseModelAssembler;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<PhysicianResponse> addPhysician(
            @RequestBody @Valid AddPhysicianRequest physicianRequest,
            UriComponentsBuilder uriBuilder
    ) {
        Physician physician = repository.save(PhysicianMapper.mapToPhysician(physicianRequest));

        URI uri = uriBuilder.path("/physicians/{id}").buildAndExpand(physician.getId()).toUri();
        PhysicianResponse physicianResponse = PhysicianMapper.mapToPhysicianResponse(physician);

        return ResponseEntity.created(uri).body(physicianResponse); // 201
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<PhysicianResponse>>> getAllPhysicians(
            @PageableDefault(size = 15, sort = {"name"})
            Pageable pageable
    ) {
        Page<PhysicianResponse> page = repository.findAllByActiveTrue(pageable)
                .map(PhysicianMapper::mapToPhysicianResponse);

        PagedModel<EntityModel<PhysicianResponse>> pagedModel = pagedResourcesAssembler.toModel(page, physicianResponseModelAssembler);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicianResponse> getPhysicianById(@PathVariable Long id) {
        Physician physician = repository.getReferenceById(id);

        return ResponseEntity.ok(PhysicianMapper.mapToPhysicianResponse(physician)); // 200
    }

    @Transactional
    @PutMapping
    public ResponseEntity<PhysicianResponse> updatePhysician(@RequestBody @Valid UpdatePhysicianRequest update) {
        Physician physician = repository.getReferenceById(update.id());
        physician.update(update);

        return ResponseEntity.ok(PhysicianMapper.mapToPhysicianResponse(physician));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePhysician(@PathVariable Long id) {
        Physician physician = repository.getReferenceById(id);
        physician.deactivate();

        return ResponseEntity.noContent().build(); // 204
    }
}
