package com.jsr_dev.medical_api.controller;

import com.jsr_dev.medical_api.physician.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physicians")
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
    public void addPhysician(@RequestBody @Valid AddPhysicianRequest physicianRequest) {
        repository.save(PhysicianMapper.mapToPhysician(physicianRequest));
    }

    @GetMapping
    public PagedModel<EntityModel<PhysicianResponse>> getAllPhysicians(
            @PageableDefault(size = 15, sort = {"name"})
            Pageable pageable
    ) {
        Page<PhysicianResponse> page = repository.findAllByActiveTrue(pageable)
                .map(PhysicianMapper::mapToPhysicianResponse);
        return pagedResourcesAssembler.toModel(page, physicianResponseModelAssembler);
    }

    @Transactional
    @PutMapping
    public PhysicianResponse updatePhysician(@RequestBody @Valid UpdatePhysicianRequest update) {
        Physician physician = repository.getReferenceById(update.id());
        physician.update(update);

        return PhysicianMapper.mapToPhysicianResponse(physician);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deletePhysician(@PathVariable Long id) {
        Physician physician = repository.getReferenceById(id);
        physician.deactivate();
    }
}
